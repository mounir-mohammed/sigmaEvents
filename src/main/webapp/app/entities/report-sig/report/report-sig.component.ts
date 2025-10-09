import { Component, OnDestroy, OnInit } from '@angular/core';
import { ChartOptions, ChartData } from 'chart.js';
import ChartDataLabels from 'chartjs-plugin-datalabels';
import { Chart } from 'chart.js';
import jsPDF from 'jspdf';
import html2canvas from 'html2canvas';
import * as XLSX from 'xlsx';
import { ReportSigService } from '../service/report.service';
import { AccreditationStatsDTO } from '../report-sig.model';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { Authority } from 'app/config/authority.constants';
import { interval, Subscription } from 'rxjs';
import { TranslateService } from '@ngx-translate/core';

Chart.register(ChartDataLabels);

@Component({
  selector: 'report-setting-sig',
  templateUrl: './report-sig.component.html',
  styleUrls: ['./report-sig.component.css'],
})
export class ReportSigComponent implements OnInit, OnDestroy {
  constructor(
    private reportService: ReportSigService,
    private accountService: AccountService,
    protected translateService: TranslateService
  ) {}

  isLoading = false;
  eventName: any;
  viewType: 'bar' | 'line' | 'donut' | 'table' = 'bar';
  selectedEventId = 0; // You can bind this dynamically later

  // === Top stats ===
  totalAccreditations = 0;
  printedAccreditations = 0;
  inProgressAccreditations = 0;

  // === Charts ===
  printedPerDayChart: ChartData<'bar'> = { labels: [], datasets: [] };
  statusChart: any = { title: '', labels: [], datasets: [] };
  donutCharts: any[] = [];

  authority = Authority;
  currentAccount: Account | null = null;
  private refreshSub?: Subscription;

  /** 🎨 Automatic pastel colors */
  private generateColors(count: number): string[] {
    const colors: string[] = [];
    const saturation = 60;
    const lightness = 65;
    for (let i = 0; i < count; i++) {
      const hue = Math.floor((360 / count) * i);
      colors.push(`hsl(${hue}, ${saturation}%, ${lightness}%)`);
    }
    return colors;
  }

  /** ⚙️ Common chart options */
  chartOptions: ChartOptions = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: { position: 'bottom' },
      datalabels: {
        color: '#000',
        font: { weight: 'bold', size: 12 },
        formatter: (value, ctx) => {
          const labels = ctx.chart.data.labels ?? [];
          const label = labels[ctx.dataIndex];

          // 🔹 Hide labels when there are too many (more than 7)
          if (labels.length > 6) {
            return ''; // no label shown
          }

          return `${label} (${value})`;
        },
      },
    },
    scales: {
      y: { beginAtZero: true },
    },
  };

  ngOnInit(): void {
    this.accountService.getAuthenticationState().subscribe(account => {
      this.currentAccount = account;
      this.eventName = this.currentAccount!.printingCentre!.event!.eventName;
      this.selectedEventId = this.currentAccount!.printingCentre!.event!.eventId;
      this.load();

      // 🔹 Puis répéter toutes les 30 secondes
      this.refreshSub = interval(30000).subscribe(() => {
        this.load();
      });
    });
  }

  /** 🧠 Load all data from backend DTO */
  load() {
    if (this.selectedEventId) {
      this.isLoading = true;

      this.reportService.getAccreditationStats(this.selectedEventId).subscribe({
        next: (data: AccreditationStatsDTO) => {
          // === Top stats ===
          this.totalAccreditations = data.totalAccreditations || 0;
          this.printedAccreditations = data.printedAccreditations || 0;
          this.inProgressAccreditations = data.inProgressAccreditations || 0;

          // === Per Day Chart ===
          const dayLabels = Object.keys(data.printedPerDay || {});
          const dayValues = Object.values(data.printedPerDay || {});
          this.printedPerDayChart = {
            labels: dayLabels,
            datasets: [
              {
                label: this.translateService.instant('sigmaEventsApp.report.home.printedAccreditationsShortLabel'),
                data: dayValues,
                backgroundColor: this.generateColors(dayValues.length),
                borderColor: '#007bff',
                borderRadius: 6,
              },
            ],
          };

          // === Status Chart ===
          const statusLabels = Object.keys(data.statusCounts || {});
          const statusValues = Object.values(data.statusCounts || {});
          this.statusChart = {
            title: this.translateService.instant('sigmaEventsApp.report.home.accreditationStatusDistributionTitle'),
            labels: statusLabels,
            datasets: [
              {
                data: statusValues,
                backgroundColor: this.generateColors(statusValues.length),
                borderColor: '#007bff',
                fill: false,
              },
            ],
          };

          // === Other donut charts ===
          this.donutCharts = [
            {
              title: this.translateService.instant('sigmaEventsApp.report.home.printedAccreditationsByCategoryLabel'),
              dataMap: data.byCategory,
            },
            {
              title: this.translateService.instant('sigmaEventsApp.report.home.printedAccreditationsByFunctionLabel'),
              dataMap: data.byFunction,
            },
            { title: this.translateService.instant('sigmaEventsApp.report.home.printedAccreditationsBySiteLabel'), dataMap: data.bySite },
            {
              title: this.translateService.instant('sigmaEventsApp.report.home.printedAccreditationsByOrganizationLabel'),
              dataMap: data.byOrganization,
            },
            { title: this.translateService.instant('sigmaEventsApp.report.home.printedAccreditationsByTypeLabel'), dataMap: data.byType },
            {
              title: this.translateService.instant('sigmaEventsApp.report.home.printedAccreditationsByNationalityLabel'),
              dataMap: data.byNationality,
            },
          ].map(chart => {
            const labels = Object.keys(chart.dataMap || {});
            const values = Object.values(chart.dataMap || {});
            return {
              title: chart.title,
              labels,
              data: values,
              datasets: [
                {
                  data: values,
                  backgroundColor: this.generateColors(labels.length),
                  borderColor: '#007bff',
                  fill: false,
                },
              ],
            };
          });

          this.isLoading = false;
        },
        error: () => (this.isLoading = false),
      });
    }
  }

  /** Return the chosen chart type */
  getChartType(): any {
    if (this.viewType === 'donut') return 'doughnut';
    if (this.viewType === 'line') return 'line';
    return 'bar';
  }

  /** 📄 Export PDF */
  async exportPDF(): Promise<void> {
    const reportElement = document.getElementById('report-content');
    if (!reportElement) return;

    this.isLoading = true;
    window.scrollTo(0, 0);

    const canvas = await html2canvas(reportElement, {
      scale: 2,
      useCORS: true,
      scrollX: 0,
      scrollY: 0,
      windowWidth: document.documentElement.scrollWidth,
      windowHeight: document.documentElement.scrollHeight,
    });

    const imgData = canvas.toDataURL('image/png');
    const pdf = new jsPDF('p', 'mm', 'a4');
    const pageWidth = pdf.internal.pageSize.getWidth();
    const pageHeight = pdf.internal.pageSize.getHeight();

    const imgWidth = pageWidth - 10;
    const imgHeight = (canvas.height * imgWidth) / canvas.width;
    let heightLeft = imgHeight;
    let position = 0;

    while (heightLeft > 0) {
      pdf.addImage(imgData, 'PNG', 5, position, imgWidth, imgHeight);
      heightLeft -= pageHeight;
      if (heightLeft > 0) {
        pdf.addPage();
        position -= pageHeight;
      }
    }

    pdf.save(this.generateFilenameWithDateTime('pdf'));
    this.isLoading = false;
  }

  /** 📊 Export Excel */
  exportExcel(): void {
    const wb = XLSX.utils.book_new();

    // Stats
    const stats = [
      ['Type', 'Valeur'],
      ['Total des accréditations', this.totalAccreditations],
      ['Accréditations imprimées', this.printedAccreditations],
      ['Accréditations en cours', this.inProgressAccreditations],
    ];
    XLSX.utils.book_append_sheet(wb, XLSX.utils.aoa_to_sheet(stats), 'Statistiques');

    // Per Day
    const perDay = [
      ['Jour', 'Nombre'],
      ...this.printedPerDayChart.labels!.map((label, i) => [label, this.printedPerDayChart.datasets[0].data[i]]),
    ];
    XLSX.utils.book_append_sheet(wb, XLSX.utils.aoa_to_sheet(perDay), 'Par jour');

    // Status
    const perStatus = [
      ['Statut', 'Valeur'],
      ...this.statusChart.labels!.map((label: any, i: string | number) => [label, this.statusChart.datasets[0].data[i]]),
    ];
    XLSX.utils.book_append_sheet(wb, XLSX.utils.aoa_to_sheet(perStatus), 'Par statut');

    // Donuts
    this.donutCharts.forEach((chart, index) => {
      const rows = [['Libellé', 'Valeur'], ...chart.labels.map((label: any, i: string | number) => [label, chart.datasets[0].data[i]])];
      let sheetName = chart.title.replace(/[\\/*?:[\]]/g, '').substring(0, 28);
      if (wb.SheetNames.includes(sheetName)) sheetName = `${sheetName}_${index + 1}`;
      XLSX.utils.book_append_sheet(wb, XLSX.utils.aoa_to_sheet(rows), sheetName);
    });

    XLSX.writeFile(wb, this.generateFilenameWithDateTime('xlsx'));
  }

  /** 📁 Helper to generate file names */
  private generateFilenameWithDateTime(extension: string): string {
    const now = new Date();
    const date = now.toISOString().slice(0, 10);
    const time = now.toTimeString().slice(0, 8).replace(/:/g, '-');
    return `${this.currentAccount!.printingCentre!.event!.eventAbreviation}_${date}_${time}.${extension}`;
  }

  ngOnDestroy(): void {
    // 🔹 Stopper le timer quand le composant est détruit
    this.refreshSub?.unsubscribe();
  }
}
