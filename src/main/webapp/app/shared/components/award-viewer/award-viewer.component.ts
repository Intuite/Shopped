import { Component, Input, OnInit } from '@angular/core';
import { AwardService } from 'app/entities/award/award.service';
import { CommendationService } from 'app/entities/commendation/commendation.service';
import { HttpResponse } from '@angular/common/http';
import { Award, IAward } from 'app/shared/model/award.model';
import { ICommendation } from 'app/shared/model/commendation.model';

@Component({
  selector: 'jhi-award-viewer',
  templateUrl: './award-viewer.component.html',
  styleUrls: ['./award-viewer.component.scss'],
})
export class AwardViewerComponent implements OnInit {
  @Input() postId: number | undefined;
  awards: Award[] = [];

  constructor(private awardService: AwardService, private commendationService: CommendationService) {}

  ngOnInit(): void {
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
      this.awardService
        .query({
          ...(idList && { 'id.in': idList }),
        })
        .subscribe(
          (res: HttpResponse<IAward[]>) => (this.awards = res.body || []),
          () => console.warn('error fetching awards')
        );
    }
  }
}
