export interface AccreditationStatsDTO {
  totalAccreditations: number;
  printedAccreditations: number;
  inProgressAccreditations: number;
  printedPerDay: Record<string, number>;
  statusCounts: Record<string, number>;
  byCategory: Record<string, number>;
  byFunction: Record<string, number>;
  bySite: Record<string, number>;
  byOrganization: Record<string, number>;
  byType: Record<string, number>;
  byNationality: Record<string, number>;
}
