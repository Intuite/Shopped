import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ShoppedTestModule } from '../../../test.module';
import { BundleDetailComponent } from 'app/entities/bundle/bundle-detail.component';
import { Bundle } from 'app/shared/model/bundle.model';

describe('Component Tests', () => {
  describe('Bundle Management Detail Component', () => {
    let comp: BundleDetailComponent;
    let fixture: ComponentFixture<BundleDetailComponent>;
    const route = ({ data: of({ bundle: new Bundle(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppedTestModule],
        declarations: [BundleDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BundleDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BundleDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load bundle on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.bundle).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
