import mysql.connector
import os
import re
import sys
from openpyxl import Workbook

# === Configuration ===
DB_HOST = 'sigmaeventsdbserver-x002.mysql.database.azure.com'
DB_USER = 'readonly_user'
DB_PASSWORD = 'MMM1740//..'
DB_NAME = 'sigmaevents'

# === Parameters ===
# === Usage: python export_photos.py 1000000 "MAROC TELECOM" ===
if len(sys.argv) != 3:
    print("❌ Usage: python export_photos.py <EVENT_ID> <ORGANIZATION_NAME>")
    sys.exit(1)

# === Parameters from command line ===
EVENT_ID = int(sys.argv[1])
ORGANIZATION_NAME = sys.argv[2]

# === Paths ===
SCRIPT_DIR = os.path.dirname(os.path.abspath(__file__))
EXPORT_BASE_DIR = os.path.join(SCRIPT_DIR, f"EVENT_{EVENT_ID}")

# Subdirectory per organization and event
SAFE_ORG_NAME = re.sub(r'[<>:"/\\|?*\t\n\r ]', '_', ORGANIZATION_NAME)
EXPORT_DIR = os.path.join(EXPORT_BASE_DIR, f"ORGANIZATION_{SAFE_ORG_NAME}")

# Create directory if not exists
os.makedirs(EXPORT_DIR, exist_ok=True)

EXCEL_FILE = os.path.join(EXPORT_DIR, f"accreditations_{EVENT_ID}_{SAFE_ORG_NAME}.xlsx")

# === Helpers ===
def sanitize_filename(filename: str) -> str:
    """Remove invalid characters for Windows file names."""
    filename = re.sub(r'[<>:"/\\|?*\t\n\r]', '_', filename)
    return filename.strip().replace(' ', '_')

def get_extension(content_type: str) -> str:
    """Return file extension from MIME type."""
    if content_type == 'image/jpeg':
        return '.jpg'
    elif content_type == 'image/png':
        return '.png'
    elif content_type == 'image/gif':
        return '.gif'
    return ''

# === Connect to MySQL ===
conn = mysql.connector.connect(
    host=DB_HOST,
    user=DB_USER,
    password=DB_PASSWORD,
    database=DB_NAME
)
cursor = conn.cursor()

# === Query including photo blob ===
query = f"""
SELECT 
    accreditation.accreditation_first_name,
    accreditation.accreditation_last_name,
    accreditation.accreditation_cin_id,
    accreditation_type.accreditation_type_value,
    fonction.fonction_name AS occupation,
    accreditation.accreditation_photo,
    accreditation.accreditation_photo_content_type,
    CONCAT(
        accreditation.accreditation_first_name, '_', accreditation.accreditation_last_name,
        CASE accreditation.accreditation_photo_content_type
            WHEN 'image/jpeg' THEN '.jpg'
            WHEN 'image/png'  THEN '.png'
            WHEN 'image/gif'  THEN '.gif'
            ELSE ''
        END
    ) AS file_name
FROM accreditation
INNER JOIN organiz ON organiz.organiz_id = accreditation.organiz_organiz_id
INNER JOIN accreditation_type ON accreditation.accreditation_type_accreditation_type_id = accreditation_type.accreditation_type_id
INNER JOIN fonction ON fonction.fonction_id = accreditation.fonction_fonction_id
WHERE accreditation.accreditation_photo IS NOT NULL
  AND accreditation.event_event_id = {EVENT_ID}
  AND organiz.organiz_name = '{ORGANIZATION_NAME}';
"""

cursor.execute(query)
rows = cursor.fetchall()
columns = [desc[0] for desc in cursor.description]

# === Prepare Excel workbook ===
wb = Workbook()
ws = wb.active
ws.title = "Accreditations"
ws.append(["First Name", "Last Name", "CIN ID", "Type", "Occupation", "File Name"])

# === Export each photo ===
exported_count = 0
for row in rows:
    (
        first_name,
        last_name,
        cin_id,
        type_value,
        occupation,
        photo_blob,
        photo_content_type,
        file_name
    ) = row

    # Determine safe filename
    ext = get_extension(photo_content_type)
    safe_name = sanitize_filename(f"{first_name}_{last_name}{ext}")
    filepath = os.path.join(EXPORT_DIR, safe_name)

    # Avoid overwriting duplicates
    base, extension = os.path.splitext(filepath)
    i = 1
    while os.path.exists(filepath):
        filepath = f"{base}_{i}{extension}"
        i += 1

    # Write photo to file
    with open(filepath, 'wb') as f:
        f.write(photo_blob)

    exported_count += 1

    # Add info to Excel (including full path)
    ws.append([first_name, last_name, cin_id, type_value, occupation, safe_name])

# Save Excel file
wb.save(EXCEL_FILE)

print("✅ Export completed successfully!")
print(f"📸 {exported_count} images saved in: {EXPORT_DIR}")
print(f"📊 Excel file created: {EXCEL_FILE}")

cursor.close()
conn.close()
