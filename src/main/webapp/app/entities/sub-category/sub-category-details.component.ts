import { Component, Vue, Inject } from 'vue-property-decorator';

import { ISubCategory } from '@/shared/model/sub-category.model';
import SubCategoryService from './sub-category.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class SubCategoryDetails extends Vue {
  @Inject('subCategoryService') private subCategoryService: () => SubCategoryService;
  @Inject('alertService') private alertService: () => AlertService;

  public subCategory: ISubCategory = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.subCategoryId) {
        vm.retrieveSubCategory(to.params.subCategoryId);
      }
    });
  }

  public retrieveSubCategory(subCategoryId) {
    this.subCategoryService()
      .find(subCategoryId)
      .then(res => {
        this.subCategory = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
