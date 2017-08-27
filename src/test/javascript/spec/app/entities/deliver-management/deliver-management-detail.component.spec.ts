/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { EmiratesGrpOnlineShopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { DeliverManagementDetailComponent } from '../../../../../../main/webapp/app/entities/deliver-management/deliver-management-detail.component';
import { DeliverManagementService } from '../../../../../../main/webapp/app/entities/deliver-management/deliver-management.service';
import { DeliverManagement } from '../../../../../../main/webapp/app/entities/deliver-management/deliver-management.model';

describe('Component Tests', () => {

    describe('DeliverManagement Management Detail Component', () => {
        let comp: DeliverManagementDetailComponent;
        let fixture: ComponentFixture<DeliverManagementDetailComponent>;
        let service: DeliverManagementService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EmiratesGrpOnlineShopTestModule],
                declarations: [DeliverManagementDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    DeliverManagementService,
                    JhiEventManager
                ]
            }).overrideTemplate(DeliverManagementDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DeliverManagementDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DeliverManagementService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new DeliverManagement(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.deliverManagement).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
