/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import AnnouncementUpdateComponent from '@/entities/announcement/announcement-update.vue';
import AnnouncementClass from '@/entities/announcement/announcement-update.component';
import AnnouncementService from '@/entities/announcement/announcement.service';

import RegionService from '@/entities/region/region.service';

import DistrictService from '@/entities/district/district.service';

import CategoryService from '@/entities/category/category.service';

import SubCategoryService from '@/entities/sub-category/sub-category.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.use(ToastPlugin);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('Announcement Management Update Component', () => {
    let wrapper: Wrapper<AnnouncementClass>;
    let comp: AnnouncementClass;
    let announcementServiceStub: SinonStubbedInstance<AnnouncementService>;

    beforeEach(() => {
      announcementServiceStub = sinon.createStubInstance<AnnouncementService>(AnnouncementService);

      wrapper = shallowMount<AnnouncementClass>(AnnouncementUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          announcementService: () => announcementServiceStub,
          alertService: () => new AlertService(),

          regionService: () =>
            sinon.createStubInstance<RegionService>(RegionService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          districtService: () =>
            sinon.createStubInstance<DistrictService>(DistrictService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          categoryService: () =>
            sinon.createStubInstance<CategoryService>(CategoryService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          subCategoryService: () =>
            sinon.createStubInstance<SubCategoryService>(SubCategoryService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      });
      comp = wrapper.vm;
    });

    describe('load', () => {
      it('Should convert date from string', () => {
        // GIVEN
        const date = new Date('2019-10-15T11:42:02Z');

        // WHEN
        const convertedDate = comp.convertDateTimeFromServer(date);

        // THEN
        expect(convertedDate).toEqual(dayjs(date).format(DATE_TIME_LONG_FORMAT));
      });

      it('Should not convert date if date is not present', () => {
        expect(comp.convertDateTimeFromServer(null)).toBeNull();
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.announcement = entity;
        announcementServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(announcementServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.announcement = entity;
        announcementServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(announcementServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundAnnouncement = { id: 123 };
        announcementServiceStub.find.resolves(foundAnnouncement);
        announcementServiceStub.retrieve.resolves([foundAnnouncement]);

        // WHEN
        comp.beforeRouteEnter({ params: { announcementId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.announcement).toBe(foundAnnouncement);
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
