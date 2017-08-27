import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { OrderMangement } from './order-mangement.model';
import { OrderMangementService } from './order-mangement.service';

@Component({
    selector: 'jhi-order-mangement-detail',
    templateUrl: './order-mangement-detail.component.html'
})
export class OrderMangementDetailComponent implements OnInit, OnDestroy {

    orderMangement: OrderMangement;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private orderMangementService: OrderMangementService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOrderMangements();
    }

    load(id) {
        this.orderMangementService.find(id).subscribe((orderMangement) => {
            this.orderMangement = orderMangement;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInOrderMangements() {
        this.eventSubscriber = this.eventManager.subscribe(
            'orderMangementListModification',
            (response) => this.load(this.orderMangement.id)
        );
    }
}
