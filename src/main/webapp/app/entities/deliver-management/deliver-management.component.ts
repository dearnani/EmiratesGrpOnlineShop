import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { DeliverManagement } from './deliver-management.model';
import { DeliverManagementService } from './deliver-management.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-deliver-management',
    templateUrl: './deliver-management.component.html'
})
export class DeliverManagementComponent implements OnInit, OnDestroy {
deliverManagements: DeliverManagement[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private deliverManagementService: DeliverManagementService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.deliverManagementService.query().subscribe(
            (res: ResponseWrapper) => {
                this.deliverManagements = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInDeliverManagements();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: DeliverManagement) {
        return item.id;
    }
    registerChangeInDeliverManagements() {
        this.eventSubscriber = this.eventManager.subscribe('deliverManagementListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
