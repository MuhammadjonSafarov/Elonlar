<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="announcement">
        <h2 class="jh-entity-heading" data-cy="announcementDetailsHeading">
          <span v-text="$t('elonlarApp.announcement.detail.title')">Announcement</span> {{ announcement.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('elonlarApp.announcement.title')">Title</span>
          </dt>
          <dd>
            <span>{{ announcement.title }}</span>
          </dd>
          <dt>
            <span v-text="$t('elonlarApp.announcement.description')">Description</span>
          </dt>
          <dd>
            <span>{{ announcement.description }}</span>
          </dd>
          <dt>
            <span v-text="$t('elonlarApp.announcement.email')">Email</span>
          </dt>
          <dd>
            <span>{{ announcement.email }}</span>
          </dd>
          <dt>
            <span v-text="$t('elonlarApp.announcement.phoneNumber')">Phone Number</span>
          </dt>
          <dd>
            <span>{{ announcement.phoneNumber }}</span>
          </dd>
          <dt>
            <span v-text="$t('elonlarApp.announcement.hireDate')">Hire Date</span>
          </dt>
          <dd>
            <span v-if="announcement.hireDate">{{ $d(Date.parse(announcement.hireDate), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('elonlarApp.announcement.salary')">Salary</span>
          </dt>
          <dd>
            <span>{{ announcement.salary }}</span>
          </dd>
          <dt>
            <span v-text="$t('elonlarApp.announcement.longitude')">Longitude</span>
          </dt>
          <dd>
            <span>{{ announcement.longitude }}</span>
          </dd>
          <dt>
            <span v-text="$t('elonlarApp.announcement.latitude')">Latitude</span>
          </dt>
          <dd>
            <span>{{ announcement.latitude }}</span>
          </dd>
          <dt>
            <span v-text="$t('elonlarApp.announcement.status')">Status</span>
          </dt>
          <dd>
            <span v-text="$t('elonlarApp.AnnouncementStatus.' + announcement.status)">{{ announcement.status }}</span>
          </dd>
          <dt>
            <span v-text="$t('elonlarApp.announcement.region')">Region</span>
          </dt>
          <dd>
            <div v-if="announcement.region">
              <router-link :to="{ name: 'RegionView', params: { regionId: announcement.region.id } }">{{
                announcement.region.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('elonlarApp.announcement.district')">District</span>
          </dt>
          <dd>
            <div v-if="announcement.district">
              <router-link :to="{ name: 'DistrictView', params: { districtId: announcement.district.id } }">{{
                announcement.district.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('elonlarApp.announcement.category')">Category</span>
          </dt>
          <dd>
            <div v-if="announcement.category">
              <router-link :to="{ name: 'CategoryView', params: { categoryId: announcement.category.id } }">{{
                announcement.category.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('elonlarApp.announcement.subcategory')">Subcategory</span>
          </dt>
          <dd>
            <div v-if="announcement.subcategory">
              <router-link :to="{ name: 'SubCategoryView', params: { subCategoryId: announcement.subcategory.id } }">{{
                announcement.subcategory.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="announcement.id"
          :to="{ name: 'AnnouncementEdit', params: { announcementId: announcement.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./announcement-details.component.ts"></script>
