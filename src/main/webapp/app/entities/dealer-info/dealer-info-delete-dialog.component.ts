import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DealerInfo } from './dealer-info.model';
import { DealerInfoPopupService } from './dealer-info-popup.service';
import { DealerInfoService } from './dealer-info.service';

@Component({
    selector: 'jhi-dealer-info-delete-dialog',
    templateUrl: './dealer-info-delete-dialog.component.html'
})
export class DealerInfoDeleteDialogComponent {

    dealerInfo: DealerInfo;

    constructor(
        private dealerInfoService: DealerInfoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.dealerInfoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'dealerInfoListModification',
                content: 'Deleted an dealerInfo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-dealer-info-delete-popup',
    template: ''
})
export class DealerInfoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dealerInfoPopupService: DealerInfoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.dealerInfoPopupService
                .open(DealerInfoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
