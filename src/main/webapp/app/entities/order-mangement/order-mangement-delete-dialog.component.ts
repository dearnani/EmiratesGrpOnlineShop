import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { OrderMangement } from './order-mangement.model';
import { OrderMangementPopupService } from './order-mangement-popup.service';
import { OrderMangementService } from './order-mangement.service';

@Component({
    selector: 'jhi-order-mangement-delete-dialog',
    templateUrl: './order-mangement-delete-dialog.component.html'
})
export class OrderMangementDeleteDialogComponent {

    orderMangement: OrderMangement;

    constructor(
        private orderMangementService: OrderMangementService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.orderMangementService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'orderMangementListModification',
                content: 'Deleted an orderMangement'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-order-mangement-delete-popup',
    template: ''
})
export class OrderMangementDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private orderMangementPopupService: OrderMangementPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.orderMangementPopupService
                .open(OrderMangementDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
