import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { DealerInfo } from './dealer-info.model';
import { DealerInfoService } from './dealer-info.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-dealer-info',
    templateUrl: './dealer-info.component.html'
})
export class DealerInfoComponent implements OnInit, OnDestroy {
dealerInfos: DealerInfo[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private dealerInfoService: DealerInfoService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.dealerInfoService.query().subscribe(
            (res: ResponseWrapper) => {
                this.dealerInfos = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInDealerInfos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: DealerInfo) {
        return item.id;
    }
    registerChangeInDealerInfos() {
        this.eventSubscriber = this.eventManager.subscribe('dealerInfoListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
