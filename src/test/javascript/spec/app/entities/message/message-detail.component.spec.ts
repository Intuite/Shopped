import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ShoppedTestModule } from '../../../test.module';
import { MessageDetailComponent } from 'app/entities/message/message-detail.component';
import { Message } from 'app/shared/model/message.model';

describe('Component Tests', () => {
  describe('Message Management Detail Component', () => {
    let comp: MessageDetailComponent;
    let fixture: ComponentFixture<MessageDetailComponent>;
    const route = ({ data: of({ message: new Message(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppedTestModule],
        declarations: [MessageDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(MessageDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MessageDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load message on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.message).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
