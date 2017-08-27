import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { DealerInfo } from './dealer-info.model';
import { DealerInfoService } from './dealer-info.service';

@Component({
    selector: 'jhi-dealer-info-detail',
    templateUrl: './dealer-info-detail.component.html'
})
export class DealerInfoDetailComponent implements OnInit, OnDestroy {

    dealerInfo: DealerInfo;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dealerInfoService: DealerInfoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDealerInfos();
    }

    load(id) {
        this.dealerInfoService.find(id).subscribe((dealerInfo) => {
            this.dealerInfo = dealerInfo;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDealerInfos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'dealerInfoListModification',
            (response) => this.load(this.dealerInfo.id)
        );
    }
}
