import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import RegionService from '@/entities/region/region.service';
import { IRegion } from '@/shared/model/region.model';

import DistrictService from '@/entities/district/district.service';
import { IDistrict } from '@/shared/model/district.model';

import CategoryService from '@/entities/category/category.service';
import { ICategory } from '@/shared/model/category.model';

import SubCategoryService from '@/entities/sub-category/sub-category.service';
import { ISubCategory } from '@/shared/model/sub-category.model';

import { IAnnouncement, Announcement } from '@/shared/model/announcement.model';
import AnnouncementService from './announcement.service';
import { AnnouncementStatus } from '@/shared/model/enumerations/announcement-status.model';

const validations: any = {
  announcement: {
    title: {},
    description: {},
    email: {},
    phoneNumber: {},
    hireDate: {},
    salary: {},
    longitude: {},
    latitude: {},
    status: {},
  },
};

@Component({
  validations,
})
export default class AnnouncementUpdate extends Vue {
  @Inject('announcementService') private announcementService: () => AnnouncementService;
  @Inject('alertService') private alertService: () => AlertService;

  public announcement: IAnnouncement = new Announcement();

  @Inject('regionService') private regionService: () => RegionService;

  public regions: IRegion[] = [];

  @Inject('districtService') private districtService: () => DistrictService;

  public districts: IDistrict[] = [];

  @Inject('categoryService') private categoryService: () => CategoryService;

  public categories: ICategory[] = [];

  @Inject('subCategoryService') private subCategoryService: () => SubCategoryService;

  public subCategories: ISubCategory[] = [];
  public announcementStatusValues: string[] = Object.keys(AnnouncementStatus);
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.announcementId) {
        vm.retrieveAnnouncement(to.params.announcementId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.announcement.id) {
      this.announcementService()
        .update(this.announcement)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('elonlarApp.announcement.updated', { param: param.id });
          return (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.announcementService()
        .create(this.announcement)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('elonlarApp.announcement.created', { param: param.id });
          (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public convertDateTimeFromServer(date: Date): string {
    if (date && dayjs(date).isValid()) {
      return dayjs(date).format(DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.announcement[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.announcement[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.announcement[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.announcement[field] = null;
    }
  }

  public retrieveAnnouncement(announcementId): void {
    this.announcementService()
      .find(announcementId)
      .then(res => {
        res.hireDate = new Date(res.hireDate);
        this.announcement = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.regionService()
      .retrieve()
      .then(res => {
        this.regions = res.data;
      });
    this.districtService()
      .retrieve()
      .then(res => {
        this.districts = res.data;
      });
    this.categoryService()
      .retrieve()
      .then(res => {
        this.categories = res.data;
      });
    this.subCategoryService()
      .retrieve()
      .then(res => {
        this.subCategories = res.data;
      });
  }
}
