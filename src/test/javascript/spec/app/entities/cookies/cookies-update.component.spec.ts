import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ShoppedTestModule } from '../../../test.module';
import { CookiesUpdateComponent } from 'app/entities/cookies/cookies-update.component';
import { CookiesService } from 'app/entities/cookies/cookies.service';
import { Cookies } from 'app/shared/model/cookies.model';

describe('Component Tests', () => {
  describe('Cookies Management Update Component', () => {
    let comp: CookiesUpdateComponent;
    let fixture: ComponentFixture<CookiesUpdateComponent>;
    let service: CookiesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppedTestModule],
        declarations: [CookiesUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CookiesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CookiesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CookiesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Cookies(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Cookies();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
