import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ShoppedTestModule } from '../../../test.module';
import { BundleComponent } from 'app/entities/bundle/bundle.component';
import { BundleService } from 'app/entities/bundle/bundle.service';
import { Bundle } from 'app/shared/model/bundle.model';

describe('Component Tests', () => {
  describe('Bundle Management Component', () => {
    let comp: BundleComponent;
    let fixture: ComponentFixture<BundleComponent>;
    let service: BundleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppedTestModule],
        declarations: [BundleComponent],
      })
        .overrideTemplate(BundleComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BundleComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BundleService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Bundle(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.bundles && comp.bundles[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
