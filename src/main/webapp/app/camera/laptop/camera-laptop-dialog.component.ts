import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { AccountService } from 'app/core/auth/account.service';
import { PHOTO_CAPTURED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './camera-laptop-dialog.component.html',
})
export class CameraLaptopDialogComponent implements OnInit {
  @ViewChild('videoElement') videoElement!: ElementRef<HTMLVideoElement>;
  private videoStream: MediaStream | undefined;
  capturedImageData: any = null;

  constructor(protected activeModal: NgbActiveModal, protected accountService: AccountService) {}

  ngOnInit(): void {
    this.startCamera();
  }

  cancel(): void {
    this.stopCamera();
    this.activeModal.dismiss();
  }

  confirmCapturePhoto(): void {
    if (this.capturedImageData) {
      this.activeModal.close(this.capturedImageData);
    } else {
      console.log('NO DATA FOUND');
    }
  }

  startCamera(): void {
    if (navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {
      navigator.mediaDevices
        .getUserMedia({ video: true })
        .then((stream: MediaStream) => {
          const videoElement = this.videoElement.nativeElement;
          videoElement.srcObject = stream;
          videoElement.play();
        })
        .catch((error: any) => {
          console.error('Error accessing camera:', error);
          alert('Camera access denied. Please grant permission to use the camera.');
        });
    } else {
      console.error('No Camera Found');
      alert('No Camera Found');
      this.cancel();
    }
  }
  stopCamera(): void {
    if (this.videoStream) {
      this.videoStream.getTracks().forEach(track => track.stop());
    }
  }

  capturePhoto(): void {
    const videoElement = this.videoElement.nativeElement;
    const canvas = document.createElement('canvas');
    canvas.width = videoElement.videoWidth;
    canvas.height = videoElement.videoHeight;

    const context = canvas.getContext('2d');
    context?.drawImage(videoElement, 0, 0, canvas.width, canvas.height);

    const capturedImageData = canvas.toDataURL('image/jpeg', '1.0');
    if (capturedImageData) {
      this.capturedImageData = capturedImageData.replace('data:image/jpeg;base64,', '');
    }
    this.stopCamera();
  }
}
