import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { PaymentDetails } from './payment-details.model';
import { PaymentDetailsService } from './payment-details.service';

@Injectable()
export class PaymentDetailsPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private paymentDetailsService: PaymentDetailsService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.paymentDetailsService.find(id).subscribe((paymentDetails) => {
                    paymentDetails.oderDate = this.datePipe
                        .transform(paymentDetails.oderDate, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.paymentDetailsModalRef(component, paymentDetails);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.paymentDetailsModalRef(component, new PaymentDetails());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    paymentDetailsModalRef(component: Component, paymentDetails: PaymentDetails): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.paymentDetails = paymentDetails;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
