/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { EmiratesGrpOnlineShopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { OrderMangementDetailComponent } from '../../../../../../main/webapp/app/entities/order-mangement/order-mangement-detail.component';
import { OrderMangementService } from '../../../../../../main/webapp/app/entities/order-mangement/order-mangement.service';
import { OrderMangement } from '../../../../../../main/webapp/app/entities/order-mangement/order-mangement.model';

describe('Component Tests', () => {

    describe('OrderMangement Management Detail Component', () => {
        let comp: OrderMangementDetailComponent;
        let fixture: ComponentFixture<OrderMangementDetailComponent>;
        let service: OrderMangementService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EmiratesGrpOnlineShopTestModule],
                declarations: [OrderMangementDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    OrderMangementService,
                    JhiEventManager
                ]
            }).overrideTemplate(OrderMangementDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OrderMangementDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrderMangementService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new OrderMangement(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.orderMangement).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
