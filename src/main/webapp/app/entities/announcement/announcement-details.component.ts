import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAnnouncement } from '@/shared/model/announcement.model';
import AnnouncementService from './announcement.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class AnnouncementDetails extends Vue {
  @Inject('announcementService') private announcementService: () => AnnouncementService;
  @Inject('alertService') private alertService: () => AlertService;

  public announcement: IAnnouncement = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.announcementId) {
        vm.retrieveAnnouncement(to.params.announcementId);
      }
    });
  }

  public retrieveAnnouncement(announcementId) {
    this.announcementService()
      .find(announcementId)
      .then(res => {
        this.announcement = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
