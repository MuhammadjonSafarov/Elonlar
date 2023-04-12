<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="elonlarApp.district.home.createOrEditLabel"
          data-cy="DistrictCreateUpdateHeading"
          v-text="$t('elonlarApp.district.home.createOrEditLabel')"
        >
          Create or edit a District
        </h2>
        <div>
          <div class="form-group" v-if="district.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="district.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('elonlarApp.district.nameUzLat')" for="district-nameUzLat">Name Uz Lat</label>
            <input
              type="text"
              class="form-control"
              name="nameUzLat"
              id="district-nameUzLat"
              data-cy="nameUzLat"
              :class="{ valid: !$v.district.nameUzLat.$invalid, invalid: $v.district.nameUzLat.$invalid }"
              v-model="$v.district.nameUzLat.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('elonlarApp.district.nameUzKr')" for="district-nameUzKr">Name Uz Kr</label>
            <input
              type="text"
              class="form-control"
              name="nameUzKr"
              id="district-nameUzKr"
              data-cy="nameUzKr"
              :class="{ valid: !$v.district.nameUzKr.$invalid, invalid: $v.district.nameUzKr.$invalid }"
              v-model="$v.district.nameUzKr.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('elonlarApp.district.nameRu')" for="district-nameRu">Name Ru</label>
            <input
              type="text"
              class="form-control"
              name="nameRu"
              id="district-nameRu"
              data-cy="nameRu"
              :class="{ valid: !$v.district.nameRu.$invalid, invalid: $v.district.nameRu.$invalid }"
              v-model="$v.district.nameRu.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('elonlarApp.district.nameEn')" for="district-nameEn">Name En</label>
            <input
              type="text"
              class="form-control"
              name="nameEn"
              id="district-nameEn"
              data-cy="nameEn"
              :class="{ valid: !$v.district.nameEn.$invalid, invalid: $v.district.nameEn.$invalid }"
              v-model="$v.district.nameEn.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('elonlarApp.district.longitude')" for="district-longitude">Longitude</label>
            <input
              type="number"
              class="form-control"
              name="longitude"
              id="district-longitude"
              data-cy="longitude"
              :class="{ valid: !$v.district.longitude.$invalid, invalid: $v.district.longitude.$invalid }"
              v-model.number="$v.district.longitude.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('elonlarApp.district.latitude')" for="district-latitude">Latitude</label>
            <input
              type="number"
              class="form-control"
              name="latitude"
              id="district-latitude"
              data-cy="latitude"
              :class="{ valid: !$v.district.latitude.$invalid, invalid: $v.district.latitude.$invalid }"
              v-model.number="$v.district.latitude.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('elonlarApp.district.region')" for="district-region">Region</label>
            <select class="form-control" id="district-region" data-cy="region" name="region" v-model="district.region">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="district.region && regionOption.id === district.region.id ? district.region : regionOption"
                v-for="regionOption in regions"
                :key="regionOption.id"
              >
                {{ regionOption.id }}
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
            :disabled="$v.district.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./district-update.component.ts"></script>
