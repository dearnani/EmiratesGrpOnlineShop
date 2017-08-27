/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { EmiratesGrpOnlineShopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ProductInfoDetailComponent } from '../../../../../../main/webapp/app/entities/product-info/product-info-detail.component';
import { ProductInfoService } from '../../../../../../main/webapp/app/entities/product-info/product-info.service';
import { ProductInfo } from '../../../../../../main/webapp/app/entities/product-info/product-info.model';

describe('Component Tests', () => {

    describe('ProductInfo Management Detail Component', () => {
        let comp: ProductInfoDetailComponent;
        let fixture: ComponentFixture<ProductInfoDetailComponent>;
        let service: ProductInfoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EmiratesGrpOnlineShopTestModule],
                declarations: [ProductInfoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ProductInfoService,
                    JhiEventManager
                ]
            }).overrideTemplate(ProductInfoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProductInfoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductInfoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ProductInfo(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.productInfo).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
