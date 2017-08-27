import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { DeliverManagement } from './deliver-management.model';
import { DeliverManagementService } from './deliver-management.service';

@Component({
    selector: 'jhi-deliver-management-detail',
    templateUrl: './deliver-management-detail.component.html'
})
export class DeliverManagementDetailComponent implements OnInit, OnDestroy {

    deliverManagement: DeliverManagement;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private deliverManagementService: DeliverManagementService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDeliverManagements();
    }

    load(id) {
        this.deliverManagementService.find(id).subscribe((deliverManagement) => {
            this.deliverManagement = deliverManagement;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDeliverManagements() {
        this.eventSubscriber = this.eventManager.subscribe(
            'deliverManagementListModification',
            (response) => this.load(this.deliverManagement.id)
        );
    }
}
