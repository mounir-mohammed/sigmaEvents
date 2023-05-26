import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { AccountService } from 'app/core/auth/account.service';

@Component({
  templateUrl: './camera-phone-dialog.component.html',
})
export class CameraPhoneDialogComponent implements OnInit {
  @ViewChild('videoElement') videoElement!: ElementRef<HTMLVideoElement>;
  private videoStream: MediaStream | undefined;

  constructor(protected activeModal: NgbActiveModal, protected accountService: AccountService) {}

  ngOnInit(): void {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  capturePhoto(): void {}

  handleFileInput(event: any): void {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = (e: any) => {
        const capturedImageData = e.target.result;
        // Handle the captured photo data as needed
      };
      reader.readAsDataURL(file);
    }
  }
}
