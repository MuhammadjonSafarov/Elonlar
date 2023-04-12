<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="elonlarApp.announcement.home.createOrEditLabel"
          data-cy="AnnouncementCreateUpdateHeading"
          v-text="$t('elonlarApp.announcement.home.createOrEditLabel')"
        >
          Create or edit a Announcement
        </h2>
        <div>
          <div class="form-group" v-if="announcement.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="announcement.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('elonlarApp.announcement.title')" for="announcement-title">Title</label>
            <input
              type="text"
              class="form-control"
              name="title"
              id="announcement-title"
              data-cy="title"
              :class="{ valid: !$v.announcement.title.$invalid, invalid: $v.announcement.title.$invalid }"
              v-model="$v.announcement.title.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('elonlarApp.announcement.description')" for="announcement-description"
              >Description</label
            >
            <input
              type="text"
              class="form-control"
              name="description"
              id="announcement-description"
              data-cy="description"
              :class="{ valid: !$v.announcement.description.$invalid, invalid: $v.announcement.description.$invalid }"
              v-model="$v.announcement.description.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('elonlarApp.announcement.email')" for="announcement-email">Email</label>
            <input
              type="text"
              class="form-control"
              name="email"
              id="announcement-email"
              data-cy="email"
              :class="{ valid: !$v.announcement.email.$invalid, invalid: $v.announcement.email.$invalid }"
              v-model="$v.announcement.email.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('elonlarApp.announcement.phoneNumber')" for="announcement-phoneNumber"
              >Phone Number</label
            >
            <input
              type="text"
              class="form-control"
              name="phoneNumber"
              id="announcement-phoneNumber"
              data-cy="phoneNumber"
              :class="{ valid: !$v.announcement.phoneNumber.$invalid, invalid: $v.announcement.phoneNumber.$invalid }"
              v-model="$v.announcement.phoneNumber.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('elonlarApp.announcement.hireDate')" for="announcement-hireDate">Hire Date</label>
            <div class="d-flex">
              <input
                id="announcement-hireDate"
                data-cy="hireDate"
                type="datetime-local"
                class="form-control"
                name="hireDate"
                :class="{ valid: !$v.announcement.hireDate.$invalid, invalid: $v.announcement.hireDate.$invalid }"
                :value="convertDateTimeFromServer($v.announcement.hireDate.$model)"
                @change="updateInstantField('hireDate', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('elonlarApp.announcement.salary')" for="announcement-salary">Salary</label>
            <input
              type="number"
              class="form-control"
              name="salary"
              id="announcement-salary"
              data-cy="salary"
              :class="{ valid: !$v.announcement.salary.$invalid, invalid: $v.announcement.salary.$invalid }"
              v-model.number="$v.announcement.salary.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('elonlarApp.announcement.longitude')" for="announcement-longitude"
              >Longitude</label
            >
            <input
              type="number"
              class="form-control"
              name="longitude"
              id="announcement-longitude"
              data-cy="longitude"
              :class="{ valid: !$v.announcement.longitude.$invalid, invalid: $v.announcement.longitude.$invalid }"
              v-model.number="$v.announcement.longitude.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('elonlarApp.announcement.latitude')" for="announcement-latitude">Latitude</label>
            <input
              type="number"
              class="form-control"
              name="latitude"
              id="announcement-latitude"
              data-cy="latitude"
              :class="{ valid: !$v.announcement.latitude.$invalid, invalid: $v.announcement.latitude.$invalid }"
              v-model.number="$v.announcement.latitude.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('elonlarApp.announcement.status')" for="announcement-status">Status</label>
            <select
              class="form-control"
              name="status"
              :class="{ valid: !$v.announcement.status.$invalid, invalid: $v.announcement.status.$invalid }"
              v-model="$v.announcement.status.$model"
              id="announcement-status"
              data-cy="status"
            >
              <option
                v-for="announcementStatus in announcementStatusValues"
                :key="announcementStatus"
                v-bind:value="announcementStatus"
                v-bind:label="$t('elonlarApp.AnnouncementStatus.' + announcementStatus)"
              >
                {{ announcementStatus }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('elonlarApp.announcement.region')" for="announcement-region">Region</label>
            <select class="form-control" id="announcement-region" data-cy="region" name="region" v-model="announcement.region">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="announcement.region && regionOption.id === announcement.region.id ? announcement.region : regionOption"
                v-for="regionOption in regions"
                :key="regionOption.id"
              >
                {{ regionOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('elonlarApp.announcement.district')" for="announcement-district">District</label>
            <select class="form-control" id="announcement-district" data-cy="district" name="district" v-model="announcement.district">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  announcement.district && districtOption.id === announcement.district.id ? announcement.district : districtOption
                "
                v-for="districtOption in districts"
                :key="districtOption.id"
              >
                {{ districtOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('elonlarApp.announcement.category')" for="announcement-category">Category</label>
            <select class="form-control" id="announcement-category" data-cy="category" name="category" v-model="announcement.category">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  announcement.category && categoryOption.id === announcement.category.id ? announcement.category : categoryOption
                "
                v-for="categoryOption in categories"
                :key="categoryOption.id"
              >
                {{ categoryOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('elonlarApp.announcement.subcategory')" for="announcement-subcategory"
              >Subcategory</label
            >
            <select
              class="form-control"
              id="announcement-subcategory"
              data-cy="subcategory"
              name="subcategory"
              v-model="announcement.subcategory"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  announcement.subcategory && subCategoryOption.id === announcement.subcategory.id
                    ? announcement.subcategory
                    : subCategoryOption
                "
                v-for="subCategoryOption in subCategories"
                :key="subCategoryOption.id"
              >
                {{ subCategoryOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.announcement.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./announcement-update.component.ts"></script>
