import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITagType } from 'app/shared/model/tag-type.model';
import { TagTypeService } from './tag-type.service';

@Component({
  templateUrl: './tag-type-delete-dialog.component.html',
})
export class TagTypeDeleteDialogComponent {
  tagType?: ITagType;

  constructor(protected tagTypeService: TagTypeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tagTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tagTypeListModification');
      this.activeModal.close();
    });
  }
}
