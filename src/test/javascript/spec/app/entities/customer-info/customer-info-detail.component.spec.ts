/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { EmiratesGrpOnlineShopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CustomerInfoDetailComponent } from '../../../../../../main/webapp/app/entities/customer-info/customer-info-detail.component';
import { CustomerInfoService } from '../../../../../../main/webapp/app/entities/customer-info/customer-info.service';
import { CustomerInfo } from '../../../../../../main/webapp/app/entities/customer-info/customer-info.model';

describe('Component Tests', () => {

    describe('CustomerInfo Management Detail Component', () => {
        let comp: CustomerInfoDetailComponent;
        let fixture: ComponentFixture<CustomerInfoDetailComponent>;
        let service: CustomerInfoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EmiratesGrpOnlineShopTestModule],
                declarations: [CustomerInfoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CustomerInfoService,
                    JhiEventManager
                ]
            }).overrideTemplate(CustomerInfoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CustomerInfoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CustomerInfoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CustomerInfo(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.customerInfo).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
