<template>
  <div>
    <h2 id="page-heading" data-cy="AnnouncementHeading">
      <span v-text="$t('elonlarApp.announcement.home.title')" id="announcement-heading">Announcements</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('elonlarApp.announcement.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'AnnouncementCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-announcement"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('elonlarApp.announcement.home.createLabel')"> Create a new Announcement </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && announcements && announcements.length === 0">
      <span v-text="$t('elonlarApp.announcement.home.notFound')">No announcements found</span>
    </div>
    <div class="table-responsive" v-if="announcements && announcements.length > 0">
      <table class="table table-striped" aria-describedby="announcements">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('title')">
              <span v-text="$t('elonlarApp.announcement.title')">Title</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'title'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('description')">
              <span v-text="$t('elonlarApp.announcement.description')">Description</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('email')">
              <span v-text="$t('elonlarApp.announcement.email')">Email</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'email'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('phoneNumber')">
              <span v-text="$t('elonlarApp.announcement.phoneNumber')">Phone Number</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'phoneNumber'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('hireDate')">
              <span v-text="$t('elonlarApp.announcement.hireDate')">Hire Date</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'hireDate'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('salary')">
              <span v-text="$t('elonlarApp.announcement.salary')">Salary</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'salary'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('longitude')">
              <span v-text="$t('elonlarApp.announcement.longitude')">Longitude</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'longitude'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('latitude')">
              <span v-text="$t('elonlarApp.announcement.latitude')">Latitude</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'latitude'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('status')">
              <span v-text="$t('elonlarApp.announcement.status')">Status</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'status'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('region.id')">
              <span v-text="$t('elonlarApp.announcement.region')">Region</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'region.id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('district.id')">
              <span v-text="$t('elonlarApp.announcement.district')">District</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'district.id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('category.id')">
              <span v-text="$t('elonlarApp.announcement.category')">Category</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'category.id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('subcategory.id')">
              <span v-text="$t('elonlarApp.announcement.subcategory')">Subcategory</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'subcategory.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="announcement in announcements" :key="announcement.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'AnnouncementView', params: { announcementId: announcement.id } }">{{
                announcement.id
              }}</router-link>
            </td>
            <td>{{ announcement.title }}</td>
            <td>{{ announcement.description }}</td>
            <td>{{ announcement.email }}</td>
            <td>{{ announcement.phoneNumber }}</td>
            <td>{{ announcement.hireDate ? $d(Date.parse(announcement.hireDate), 'short') : '' }}</td>
            <td>{{ announcement.salary }}</td>
            <td>{{ announcement.longitude }}</td>
            <td>{{ announcement.latitude }}</td>
            <td v-text="$t('elonlarApp.AnnouncementStatus.' + announcement.status)">{{ announcement.status }}</td>
            <td>
              <div v-if="announcement.region">
                <router-link :to="{ name: 'RegionView', params: { regionId: announcement.region.id } }">{{
                  announcement.region.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="announcement.district">
                <router-link :to="{ name: 'DistrictView', params: { districtId: announcement.district.id } }">{{
                  announcement.district.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="announcement.category">
                <router-link :to="{ name: 'CategoryView', params: { categoryId: announcement.category.id } }">{{
                  announcement.category.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="announcement.subcategory">
                <router-link :to="{ name: 'SubCategoryView', params: { subCategoryId: announcement.subcategory.id } }">{{
                  announcement.subcategory.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'AnnouncementView', params: { announcementId: announcement.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'AnnouncementEdit', params: { announcementId: announcement.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(announcement)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="elonlarApp.announcement.delete.question" data-cy="announcementDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-announcement-heading" v-text="$t('elonlarApp.announcement.delete.question', { id: removeId })">
          Are you sure you want to delete this Announcement?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-announcement"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeAnnouncement()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="announcements && announcements.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./announcement.component.ts"></script>
