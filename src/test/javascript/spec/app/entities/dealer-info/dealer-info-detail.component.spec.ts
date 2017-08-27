/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { EmiratesGrpOnlineShopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { DealerInfoDetailComponent } from '../../../../../../main/webapp/app/entities/dealer-info/dealer-info-detail.component';
import { DealerInfoService } from '../../../../../../main/webapp/app/entities/dealer-info/dealer-info.service';
import { DealerInfo } from '../../../../../../main/webapp/app/entities/dealer-info/dealer-info.model';

describe('Component Tests', () => {

    describe('DealerInfo Management Detail Component', () => {
        let comp: DealerInfoDetailComponent;
        let fixture: ComponentFixture<DealerInfoDetailComponent>;
        let service: DealerInfoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EmiratesGrpOnlineShopTestModule],
                declarations: [DealerInfoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    DealerInfoService,
                    JhiEventManager
                ]
            }).overrideTemplate(DealerInfoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DealerInfoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DealerInfoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new DealerInfo(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.dealerInfo).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
