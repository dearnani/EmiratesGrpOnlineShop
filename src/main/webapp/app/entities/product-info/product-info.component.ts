import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { ProductInfo } from './product-info.model';
import { ProductInfoService } from './product-info.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-product-info',
    templateUrl: './product-info.component.html'
})
export class ProductInfoComponent implements OnInit, OnDestroy {
productInfos: ProductInfo[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private productInfoService: ProductInfoService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.productInfoService.query().subscribe(
            (res: ResponseWrapper) => {
                this.productInfos = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInProductInfos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ProductInfo) {
        return item.id;
    }
    registerChangeInProductInfos() {
        this.eventSubscriber = this.eventManager.subscribe('productInfoListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
