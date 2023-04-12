<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="elonlarApp.region.home.createOrEditLabel"
          data-cy="RegionCreateUpdateHeading"
          v-text="$t('elonlarApp.region.home.createOrEditLabel')"
        >
          Create or edit a Region
        </h2>
        <div>
          <div class="form-group" v-if="region.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="region.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('elonlarApp.region.nameUzLat')" for="region-nameUzLat">Name Uz Lat</label>
            <input
              type="text"
              class="form-control"
              name="nameUzLat"
              id="region-nameUzLat"
              data-cy="nameUzLat"
              :class="{ valid: !$v.region.nameUzLat.$invalid, invalid: $v.region.nameUzLat.$invalid }"
              v-model="$v.region.nameUzLat.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('elonlarApp.region.nameUzKr')" for="region-nameUzKr">Name Uz Kr</label>
            <input
              type="text"
              class="form-control"
              name="nameUzKr"
              id="region-nameUzKr"
              data-cy="nameUzKr"
              :class="{ valid: !$v.region.nameUzKr.$invalid, invalid: $v.region.nameUzKr.$invalid }"
              v-model="$v.region.nameUzKr.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('elonlarApp.region.nameRu')" for="region-nameRu">Name Ru</label>
            <input
              type="text"
              class="form-control"
              name="nameRu"
              id="region-nameRu"
              data-cy="nameRu"
              :class="{ valid: !$v.region.nameRu.$invalid, invalid: $v.region.nameRu.$invalid }"
              v-model="$v.region.nameRu.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('elonlarApp.region.nameEn')" for="region-nameEn">Name En</label>
            <input
              type="text"
              class="form-control"
              name="nameEn"
              id="region-nameEn"
              data-cy="nameEn"
              :class="{ valid: !$v.region.nameEn.$invalid, invalid: $v.region.nameEn.$invalid }"
              v-model="$v.region.nameEn.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('elonlarApp.region.longitude')" for="region-longitude">Longitude</label>
            <input
              type="number"
              class="form-control"
              name="longitude"
              id="region-longitude"
              data-cy="longitude"
              :class="{ valid: !$v.region.longitude.$invalid, invalid: $v.region.longitude.$invalid }"
              v-model.number="$v.region.longitude.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('elonlarApp.region.latitude')" for="region-latitude">Latitude</label>
            <input
              type="number"
              class="form-control"
              name="latitude"
              id="region-latitude"
              data-cy="latitude"
              :class="{ valid: !$v.region.latitude.$invalid, invalid: $v.region.latitude.$invalid }"
              v-model.number="$v.region.latitude.$model"
            />
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
            :disabled="$v.region.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./region-update.component.ts"></script>
