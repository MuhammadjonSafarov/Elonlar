import { IRegion } from '@/shared/model/region.model';
import { IDistrict } from '@/shared/model/district.model';
import { ICategory } from '@/shared/model/category.model';
import { ISubCategory } from '@/shared/model/sub-category.model';

import { AnnouncementStatus } from '@/shared/model/enumerations/announcement-status.model';
export interface IAnnouncement {
  id?: number;
  title?: string | null;
  description?: string | null;
  email?: string | null;
  phoneNumber?: string | null;
  hireDate?: Date | null;
  salary?: number | null;
  longitude?: number | null;
  latitude?: number | null;
  status?: AnnouncementStatus | null;
  region?: IRegion | null;
  district?: IDistrict | null;
  category?: ICategory | null;
  subcategory?: ISubCategory | null;
}

export class Announcement implements IAnnouncement {
  constructor(
    public id?: number,
    public title?: string | null,
    public description?: string | null,
    public email?: string | null,
    public phoneNumber?: string | null,
    public hireDate?: Date | null,
    public salary?: number | null,
    public longitude?: number | null,
    public latitude?: number | null,
    public status?: AnnouncementStatus | null,
    public region?: IRegion | null,
    public district?: IDistrict | null,
    public category?: ICategory | null,
    public subcategory?: ISubCategory | null
  ) {}
}
