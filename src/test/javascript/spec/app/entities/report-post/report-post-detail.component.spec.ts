import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ShoppedTestModule } from '../../../test.module';
import { ReportPostDetailComponent } from 'app/entities/report-post/report-post-detail.component';
import { ReportPost } from 'app/shared/model/report-post.model';

describe('Component Tests', () => {
  describe('ReportPost Management Detail Component', () => {
    let comp: ReportPostDetailComponent;
    let fixture: ComponentFixture<ReportPostDetailComponent>;
    const route = ({ data: of({ reportPost: new ReportPost(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppedTestModule],
        declarations: [ReportPostDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ReportPostDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ReportPostDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load reportPost on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.reportPost).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
