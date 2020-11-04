import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ShoppedTestModule } from '../../../test.module';
import { NotificationTypeDetailComponent } from 'app/entities/notification-type/notification-type-detail.component';
import { NotificationType } from 'app/shared/model/notification-type.model';

describe('Component Tests', () => {
  describe('NotificationType Management Detail Component', () => {
    let comp: NotificationTypeDetailComponent;
    let fixture: ComponentFixture<NotificationTypeDetailComponent>;
    const route = ({ data: of({ notificationType: new NotificationType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppedTestModule],
        declarations: [NotificationTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(NotificationTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NotificationTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load notificationType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.notificationType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
