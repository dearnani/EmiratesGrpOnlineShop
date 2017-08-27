/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { EmiratesGrpOnlineShopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PaymentDetailsDetailComponent } from '../../../../../../main/webapp/app/entities/payment-details/payment-details-detail.component';
import { PaymentDetailsService } from '../../../../../../main/webapp/app/entities/payment-details/payment-details.service';
import { PaymentDetails } from '../../../../../../main/webapp/app/entities/payment-details/payment-details.model';

describe('Component Tests', () => {

    describe('PaymentDetails Management Detail Component', () => {
        let comp: PaymentDetailsDetailComponent;
        let fixture: ComponentFixture<PaymentDetailsDetailComponent>;
        let service: PaymentDetailsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EmiratesGrpOnlineShopTestModule],
                declarations: [PaymentDetailsDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PaymentDetailsService,
                    JhiEventManager
                ]
            }).overrideTemplate(PaymentDetailsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PaymentDetailsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PaymentDetailsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PaymentDetails(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.paymentDetails).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
