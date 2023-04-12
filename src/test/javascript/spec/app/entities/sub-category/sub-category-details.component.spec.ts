/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import SubCategoryDetailComponent from '@/entities/sub-category/sub-category-details.vue';
import SubCategoryClass from '@/entities/sub-category/sub-category-details.component';
import SubCategoryService from '@/entities/sub-category/sub-category.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('SubCategory Management Detail Component', () => {
    let wrapper: Wrapper<SubCategoryClass>;
    let comp: SubCategoryClass;
    let subCategoryServiceStub: SinonStubbedInstance<SubCategoryService>;

    beforeEach(() => {
      subCategoryServiceStub = sinon.createStubInstance<SubCategoryService>(SubCategoryService);

      wrapper = shallowMount<SubCategoryClass>(SubCategoryDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { subCategoryService: () => subCategoryServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundSubCategory = { id: 123 };
        subCategoryServiceStub.find.resolves(foundSubCategory);

        // WHEN
        comp.retrieveSubCategory(123);
        await comp.$nextTick();

        // THEN
        expect(comp.subCategory).toBe(foundSubCategory);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundSubCategory = { id: 123 };
        subCategoryServiceStub.find.resolves(foundSubCategory);

        // WHEN
        comp.beforeRouteEnter({ params: { subCategoryId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.subCategory).toBe(foundSubCategory);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
