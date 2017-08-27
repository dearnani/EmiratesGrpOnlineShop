import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ProductInfo } from './product-info.model';
import { ProductInfoService } from './product-info.service';

@Component({
    selector: 'jhi-product-info-detail',
    templateUrl: './product-info-detail.component.html'
})
export class ProductInfoDetailComponent implements OnInit, OnDestroy {

    productInfo: ProductInfo;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private productInfoService: ProductInfoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInProductInfos();
    }

    load(id) {
        this.productInfoService.find(id).subscribe((productInfo) => {
            this.productInfo = productInfo;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInProductInfos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'productInfoListModification',
            (response) => this.load(this.productInfo.id)
        );
    }
}
