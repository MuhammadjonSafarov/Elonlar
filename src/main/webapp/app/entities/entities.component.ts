import { Component, Provide, Vue } from 'vue-property-decorator';

import UserService from '@/entities/user/user.service';
import RegionService from './region/region.service';
import DistrictService from './district/district.service';
import AnnouncementService from './announcement/announcement.service';
import CategoryService from './category/category.service';
import SubCategoryService from './sub-category/sub-category.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

@Component
export default class Entities extends Vue {
  @Provide('userService') private userService = () => new UserService();
  @Provide('regionService') private regionService = () => new RegionService();
  @Provide('districtService') private districtService = () => new DistrictService();
  @Provide('announcementService') private announcementService = () => new AnnouncementService();
  @Provide('categoryService') private categoryService = () => new CategoryService();
  @Provide('subCategoryService') private subCategoryService = () => new SubCategoryService();
  // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
}
