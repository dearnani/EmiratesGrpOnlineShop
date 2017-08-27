import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DeliverManagement } from './deliver-management.model';
import { DeliverManagementPopupService } from './deliver-management-popup.service';
import { DeliverManagementService } from './deliver-management.service';

@Component({
    selector: 'jhi-deliver-management-delete-dialog',
    templateUrl: './deliver-management-delete-dialog.component.html'
})
export class DeliverManagementDeleteDialogComponent {

    deliverManagement: DeliverManagement;

    constructor(
        private deliverManagementService: DeliverManagementService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.deliverManagementService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'deliverManagementListModification',
                content: 'Deleted an deliverManagement'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-deliver-management-delete-popup',
    template: ''
})
export class DeliverManagementDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private deliverManagementPopupService: DeliverManagementPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.deliverManagementPopupService
                .open(DeliverManagementDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
