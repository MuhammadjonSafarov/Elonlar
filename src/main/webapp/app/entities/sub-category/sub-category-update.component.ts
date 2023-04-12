import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import CategoryService from '@/entities/category/category.service';
import { ICategory } from '@/shared/model/category.model';

import { ISubCategory, SubCategory } from '@/shared/model/sub-category.model';
import SubCategoryService from './sub-category.service';

const validations: any = {
  subCategory: {
    nameUzLat: {},
    nameUzKr: {},
    nameRu: {},
    nameEn: {},
  },
};

@Component({
  validations,
})
export default class SubCategoryUpdate extends Vue {
  @Inject('subCategoryService') private subCategoryService: () => SubCategoryService;
  @Inject('alertService') private alertService: () => AlertService;

  public subCategory: ISubCategory = new SubCategory();

  @Inject('categoryService') private categoryService: () => CategoryService;

  public categories: ICategory[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.subCategoryId) {
        vm.retrieveSubCategory(to.params.subCategoryId);
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
    if (this.subCategory.id) {
      this.subCategoryService()
        .update(this.subCategory)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('elonlarApp.subCategory.updated', { param: param.id });
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
      this.subCategoryService()
        .create(this.subCategory)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('elonlarApp.subCategory.created', { param: param.id });
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

  public retrieveSubCategory(subCategoryId): void {
    this.subCategoryService()
      .find(subCategoryId)
      .then(res => {
        this.subCategory = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.categoryService()
      .retrieve()
      .then(res => {
        this.categories = res.data;
      });
  }
}
