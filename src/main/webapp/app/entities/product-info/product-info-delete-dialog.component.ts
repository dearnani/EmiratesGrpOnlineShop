import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ProductInfo } from './product-info.model';
import { ProductInfoPopupService } from './product-info-popup.service';
import { ProductInfoService } from './product-info.service';

@Component({
    selector: 'jhi-product-info-delete-dialog',
    templateUrl: './product-info-delete-dialog.component.html'
})
export class ProductInfoDeleteDialogComponent {

    productInfo: ProductInfo;

    constructor(
        private productInfoService: ProductInfoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.productInfoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'productInfoListModification',
                content: 'Deleted an productInfo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-product-info-delete-popup',
    template: ''
})
export class ProductInfoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private productInfoPopupService: ProductInfoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.productInfoPopupService
                .open(ProductInfoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
