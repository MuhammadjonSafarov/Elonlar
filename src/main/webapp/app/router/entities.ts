import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

// prettier-ignore
const Region = () => import('@/entities/region/region.vue');
// prettier-ignore
const RegionUpdate = () => import('@/entities/region/region-update.vue');
// prettier-ignore
const RegionDetails = () => import('@/entities/region/region-details.vue');
// prettier-ignore
const District = () => import('@/entities/district/district.vue');
// prettier-ignore
const DistrictUpdate = () => import('@/entities/district/district-update.vue');
// prettier-ignore
const DistrictDetails = () => import('@/entities/district/district-details.vue');
// prettier-ignore
const Announcement = () => import('@/entities/announcement/announcement.vue');
// prettier-ignore
const AnnouncementUpdate = () => import('@/entities/announcement/announcement-update.vue');
// prettier-ignore
const AnnouncementDetails = () => import('@/entities/announcement/announcement-details.vue');
// prettier-ignore
const Category = () => import('@/entities/category/category.vue');
// prettier-ignore
const CategoryUpdate = () => import('@/entities/category/category-update.vue');
// prettier-ignore
const CategoryDetails = () => import('@/entities/category/category-details.vue');
// prettier-ignore
const SubCategory = () => import('@/entities/sub-category/sub-category.vue');
// prettier-ignore
const SubCategoryUpdate = () => import('@/entities/sub-category/sub-category-update.vue');
// prettier-ignore
const SubCategoryDetails = () => import('@/entities/sub-category/sub-category-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'region',
      name: 'Region',
      component: Region,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'region/new',
      name: 'RegionCreate',
      component: RegionUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'region/:regionId/edit',
      name: 'RegionEdit',
      component: RegionUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'region/:regionId/view',
      name: 'RegionView',
      component: RegionDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'district',
      name: 'District',
      component: District,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'district/new',
      name: 'DistrictCreate',
      component: DistrictUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'district/:districtId/edit',
      name: 'DistrictEdit',
      component: DistrictUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'district/:districtId/view',
      name: 'DistrictView',
      component: DistrictDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'announcement',
      name: 'Announcement',
      component: Announcement,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'announcement/new',
      name: 'AnnouncementCreate',
      component: AnnouncementUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'announcement/:announcementId/edit',
      name: 'AnnouncementEdit',
      component: AnnouncementUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'announcement/:announcementId/view',
      name: 'AnnouncementView',
      component: AnnouncementDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'category',
      name: 'Category',
      component: Category,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'category/new',
      name: 'CategoryCreate',
      component: CategoryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'category/:categoryId/edit',
      name: 'CategoryEdit',
      component: CategoryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'category/:categoryId/view',
      name: 'CategoryView',
      component: CategoryDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'sub-category',
      name: 'SubCategory',
      component: SubCategory,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'sub-category/new',
      name: 'SubCategoryCreate',
      component: SubCategoryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'sub-category/:subCategoryId/edit',
      name: 'SubCategoryEdit',
      component: SubCategoryUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'sub-category/:subCategoryId/view',
      name: 'SubCategoryView',
      component: SubCategoryDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
