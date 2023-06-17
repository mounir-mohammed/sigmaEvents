import { AfterViewInit, Component, ElementRef, ViewChild } from '@angular/core';
import { AccountService } from 'app/core/auth/account.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  templateUrl: './camera-laptop-dialog.component.html',
  styleUrls: ['./camera-laptop-dialog.component.scss'],
})
export class CameraLaptopDialogComponent implements AfterViewInit {
  constructor(protected activeModal: NgbActiveModal, protected accountService: AccountService) {}

  WIDTH = 640;
  HEIGHT = 480;

  @ViewChild('video')
  public video: ElementRef | undefined;

  @ViewChild('canvas')
  public canvas: ElementRef | undefined;

  mediaStream: MediaStream | undefined | null;

  captures: string[] = [];
  error: any;
  isCaptured: boolean | undefined;

  async ngAfterViewInit() {
    await this.setupDevices();
  }

  async setupDevices() {
    if (navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {
      try {
        this.mediaStream = await navigator.mediaDevices.getUserMedia({
          video: true,
        });
        if (this.mediaStream) {
          this.video!.nativeElement.srcObject = this.mediaStream;
          this.video!.nativeElement.play();
          this.error = null;
        } else {
          this.error = 'No output video device';
        }
      } catch (e) {
        this.error = e;
      }
    }
  }

  capture() {
    this.drawImageToCanvas(this.video!.nativeElement);
    this.captures.push(this.canvas!.nativeElement.toDataURL('image/png'));
    this.isCaptured = true;
  }

  removeCurrent() {
    this.isCaptured = false;
  }

  setPhoto(idx: number) {
    this.isCaptured = true;
    var image = new Image();
    image.src = this.captures[idx];
    this.drawImageToCanvas(image);
  }

  drawImageToCanvas(image: any) {
    this.canvas!.nativeElement.getContext('2d').drawImage(image, 0, 0, this.WIDTH, this.HEIGHT);
  }

  cancel(): void {
    this.stopCamera();
    this.activeModal.dismiss();
  }

  confirmCapturePhoto(): void {
    if (this.isCaptured) {
      var image = this.canvas!.nativeElement.toDataURL('image/png');
      if (image) {
        const byteString = atob(image.split(',')[1]);
        const mimeString = image.split(',')[0].split(':')[1].split(';')[0];
        const ab = new ArrayBuffer(byteString.length);
        const ia = new Uint8Array(ab);
        for (let i = 0; i < byteString.length; i++) {
          ia[i] = byteString.charCodeAt(i);
        }
        const blob = new Blob([ab], { type: mimeString });
        const file = new File([blob], 'image.png', { type: blob.type });
        this.stopCamera();
        this.activeModal.close(file);
      } else {
        alert('NO selected photo');
      }
    } else {
      alert('Please take photo');
    }
  }

  stopCamera(): void {
    if (this.mediaStream) {
      const tracks = this.mediaStream.getTracks();
      tracks.forEach(track => track.stop());
      this.mediaStream = null;
    }
  }
}
