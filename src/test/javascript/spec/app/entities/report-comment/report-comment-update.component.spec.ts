import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ShoppedTestModule } from '../../../test.module';
import { ReportCommentUpdateComponent } from 'app/entities/report-comment/report-comment-update.component';
import { ReportCommentService } from 'app/entities/report-comment/report-comment.service';
import { ReportComment } from 'app/shared/model/report-comment.model';

describe('Component Tests', () => {
  describe('ReportComment Management Update Component', () => {
    let comp: ReportCommentUpdateComponent;
    let fixture: ComponentFixture<ReportCommentUpdateComponent>;
    let service: ReportCommentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppedTestModule],
        declarations: [ReportCommentUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ReportCommentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ReportCommentUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ReportCommentService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ReportComment(123);
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
        const entity = new ReportComment();
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
