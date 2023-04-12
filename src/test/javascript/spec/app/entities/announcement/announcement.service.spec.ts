/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from '@/shared/date/filters';
import AnnouncementService from '@/entities/announcement/announcement.service';
import { Announcement } from '@/shared/model/announcement.model';
import { AnnouncementStatus } from '@/shared/model/enumerations/announcement-status.model';

const error = {
  response: {
    status: null,
    data: {
      type: null,
    },
  },
};

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  post: sinon.stub(axios, 'post'),
  put: sinon.stub(axios, 'put'),
  patch: sinon.stub(axios, 'patch'),
  delete: sinon.stub(axios, 'delete'),
};

describe('Service Tests', () => {
  describe('Announcement Service', () => {
    let service: AnnouncementService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new AnnouncementService();
      currentDate = new Date();
      elemDefault = new Announcement(123, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate, 0, 0, 0, AnnouncementStatus.ACTIVE);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            hireDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault
        );
        axiosStub.get.resolves({ data: returnedFromService });

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        axiosStub.get.rejects(error);
        return service
          .find(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should create a Announcement', async () => {
        const returnedFromService = Object.assign(
          {
            id: 123,
            hireDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            hireDate: currentDate,
          },
          returnedFromService
        );

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a Announcement', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a Announcement', async () => {
        const returnedFromService = Object.assign(
          {
            title: 'BBBBBB',
            description: 'BBBBBB',
            email: 'BBBBBB',
            phoneNumber: 'BBBBBB',
            hireDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
            salary: 1,
            longitude: 1,
            latitude: 1,
            status: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            hireDate: currentDate,
          },
          returnedFromService
        );
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a Announcement', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a Announcement', async () => {
        const patchObject = Object.assign(
          {
            title: 'BBBBBB',
            email: 'BBBBBB',
            phoneNumber: 'BBBBBB',
            latitude: 1,
          },
          new Announcement()
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            hireDate: currentDate,
          },
          returnedFromService
        );
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a Announcement', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of Announcement', async () => {
        const returnedFromService = Object.assign(
          {
            title: 'BBBBBB',
            description: 'BBBBBB',
            email: 'BBBBBB',
            phoneNumber: 'BBBBBB',
            hireDate: dayjs(currentDate).format(DATE_TIME_FORMAT),
            salary: 1,
            longitude: 1,
            latitude: 1,
            status: 'BBBBBB',
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            hireDate: currentDate,
          },
          returnedFromService
        );
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of Announcement', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a Announcement', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a Announcement', async () => {
        axiosStub.delete.rejects(error);

        return service
          .delete(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});
