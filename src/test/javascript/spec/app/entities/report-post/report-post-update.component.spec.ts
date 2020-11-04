import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ShoppedTestModule } from '../../../test.module';
import { ReportPostUpdateComponent } from 'app/entities/report-post/report-post-update.component';
import { ReportPostService } from 'app/entities/report-post/report-post.service';
import { ReportPost } from 'app/shared/model/report-post.model';

describe('Component Tests', () => {
  describe('ReportPost Management Update Component', () => {
    let comp: ReportPostUpdateComponent;
    let fixture: ComponentFixture<ReportPostUpdateComponent>;
    let service: ReportPostService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppedTestModule],
        declarations: [ReportPostUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ReportPostUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ReportPostUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ReportPostService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ReportPost(123);
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
        const entity = new ReportPost();
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
