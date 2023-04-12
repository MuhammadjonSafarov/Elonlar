<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="elonlarApp.category.home.createOrEditLabel"
          data-cy="CategoryCreateUpdateHeading"
          v-text="$t('elonlarApp.category.home.createOrEditLabel')"
        >
          Create or edit a Category
        </h2>
        <div>
          <div class="form-group" v-if="category.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="category.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('elonlarApp.category.nameUzLat')" for="category-nameUzLat">Name Uz Lat</label>
            <input
              type="text"
              class="form-control"
              name="nameUzLat"
              id="category-nameUzLat"
              data-cy="nameUzLat"
              :class="{ valid: !$v.category.nameUzLat.$invalid, invalid: $v.category.nameUzLat.$invalid }"
              v-model="$v.category.nameUzLat.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('elonlarApp.category.nameUzKr')" for="category-nameUzKr">Name Uz Kr</label>
            <input
              type="text"
              class="form-control"
              name="nameUzKr"
              id="category-nameUzKr"
              data-cy="nameUzKr"
              :class="{ valid: !$v.category.nameUzKr.$invalid, invalid: $v.category.nameUzKr.$invalid }"
              v-model="$v.category.nameUzKr.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('elonlarApp.category.nameRu')" for="category-nameRu">Name Ru</label>
            <input
              type="text"
              class="form-control"
              name="nameRu"
              id="category-nameRu"
              data-cy="nameRu"
              :class="{ valid: !$v.category.nameRu.$invalid, invalid: $v.category.nameRu.$invalid }"
              v-model="$v.category.nameRu.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('elonlarApp.category.nameEn')" for="category-nameEn">Name En</label>
            <input
              type="text"
              class="form-control"
              name="nameEn"
              id="category-nameEn"
              data-cy="nameEn"
              :class="{ valid: !$v.category.nameEn.$invalid, invalid: $v.category.nameEn.$invalid }"
              v-model="$v.category.nameEn.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('elonlarApp.category.icon')" for="category-icon">Icon</label>
            <input
              type="text"
              class="form-control"
              name="icon"
              id="category-icon"
              data-cy="icon"
              :class="{ valid: !$v.category.icon.$invalid, invalid: $v.category.icon.$invalid }"
              v-model="$v.category.icon.$model"
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
            :disabled="$v.category.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./category-update.component.ts"></script>
