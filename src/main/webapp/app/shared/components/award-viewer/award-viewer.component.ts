import { Component, Input, OnInit } from '@angular/core';
import { AwardService } from 'app/entities/award/award.service';
import { CommendationService } from 'app/entities/commendation/commendation.service';
import { HttpResponse } from '@angular/common/http';
import { Award, IAward } from 'app/shared/model/award.model';
import { ICommendation } from 'app/shared/model/commendation.model';
import { EventEmitterServiceService } from 'app/shared/services/event-emitter-service.service';

@Component({
  selector: 'jhi-award-viewer',
  templateUrl: './award-viewer.component.html',
  styleUrls: ['./award-viewer.component.scss'],
})
export class AwardViewerComponent implements OnInit {
  @Input() postId: number | undefined;
  awards: Award[] = [];
  len = 0;

  constructor(
    private awardService: AwardService,
    private commendationService: CommendationService,
    private eventEmitter: EventEmitterServiceService
  ) {}

  ngOnInit(): void {
    if (this.eventEmitter.subsVar === undefined) {
      this.eventEmitter.subsVar = this.eventEmitter.invokeFunction.subscribe(() => this.load());
    }
    this.load();
  }

  private load(): void {
    this.commendationService
      .query({
        ...(this.postId && { 'postId.equals': this.postId }),
      })
      .subscribe(
        (res: HttpResponse<IAward[]>) => this.onSuccess(res.body),
        () => console.warn('error')
      );
  }

  private onSuccess(body: ICommendation[] | null): void {
    const idList: number[] = [];
    if (body) {
      for (const element of body) {
        if (element !== undefined) {
          idList.push(element.awardId as number);
        }
      }
      this.len = idList.length;
      this.awardService
        .query({
          ...(idList && { 'id.in': idList }),
        })
        .subscribe(
          (res: HttpResponse<IAward[]>) => (this.awards = (res.body || []).slice(0, 3)),
          () => console.warn('error fetching awards')
        );
    }
  }
}
