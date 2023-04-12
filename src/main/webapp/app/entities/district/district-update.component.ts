import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import RegionService from '@/entities/region/region.service';
import { IRegion } from '@/shared/model/region.model';

import { IDistrict, District } from '@/shared/model/district.model';
import DistrictService from './district.service';

const validations: any = {
  district: {
    nameUzLat: {},
    nameUzKr: {},
    nameRu: {},
    nameEn: {},
    longitude: {},
    latitude: {},
  },
};

@Component({
  validations,
})
export default class DistrictUpdate extends Vue {
  @Inject('districtService') private districtService: () => DistrictService;
  @Inject('alertService') private alertService: () => AlertService;

  public district: IDistrict = new District();

  @Inject('regionService') private regionService: () => RegionService;

  public regions: IRegion[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.districtId) {
        vm.retrieveDistrict(to.params.districtId);
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
    if (this.district.id) {
      this.districtService()
        .update(this.district)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('elonlarApp.district.updated', { param: param.id });
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
      this.districtService()
        .create(this.district)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('elonlarApp.district.created', { param: param.id });
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

  public retrieveDistrict(districtId): void {
    this.districtService()
      .find(districtId)
      .then(res => {
        this.district = res;
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
  }
}
