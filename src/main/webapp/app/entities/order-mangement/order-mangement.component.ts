import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { OrderMangement } from './order-mangement.model';
import { OrderMangementService } from './order-mangement.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-order-mangement',
    templateUrl: './order-mangement.component.html'
})
export class OrderMangementComponent implements OnInit, OnDestroy {
orderMangements: OrderMangement[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private orderMangementService: OrderMangementService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.orderMangementService.query().subscribe(
            (res: ResponseWrapper) => {
                this.orderMangements = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInOrderMangements();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: OrderMangement) {
        return item.id;
    }
    registerChangeInOrderMangements() {
        this.eventSubscriber = this.eventManager.subscribe('orderMangementListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
