import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ShoppedTestModule } from '../../../test.module';
import { ReportCommentDetailComponent } from 'app/entities/report-comment/report-comment-detail.component';
import { ReportComment } from 'app/shared/model/report-comment.model';

describe('Component Tests', () => {
  describe('ReportComment Management Detail Component', () => {
    let comp: ReportCommentDetailComponent;
    let fixture: ComponentFixture<ReportCommentDetailComponent>;
    const route = ({ data: of({ reportComment: new ReportComment(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppedTestModule],
        declarations: [ReportCommentDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ReportCommentDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ReportCommentDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load reportComment on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.reportComment).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
