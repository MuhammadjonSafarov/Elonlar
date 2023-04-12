/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import AnnouncementDetailComponent from '@/entities/announcement/announcement-details.vue';
import AnnouncementClass from '@/entities/announcement/announcement-details.component';
import AnnouncementService from '@/entities/announcement/announcement.service';
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
  describe('Announcement Management Detail Component', () => {
    let wrapper: Wrapper<AnnouncementClass>;
    let comp: AnnouncementClass;
    let announcementServiceStub: SinonStubbedInstance<AnnouncementService>;

    beforeEach(() => {
      announcementServiceStub = sinon.createStubInstance<AnnouncementService>(AnnouncementService);

      wrapper = shallowMount<AnnouncementClass>(AnnouncementDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { announcementService: () => announcementServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundAnnouncement = { id: 123 };
        announcementServiceStub.find.resolves(foundAnnouncement);

        // WHEN
        comp.retrieveAnnouncement(123);
        await comp.$nextTick();

        // THEN
        expect(comp.announcement).toBe(foundAnnouncement);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundAnnouncement = { id: 123 };
        announcementServiceStub.find.resolves(foundAnnouncement);

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
