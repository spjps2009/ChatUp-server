package com.org.chatup.model;

import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.DefaultApi10a;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

public class Yelp {
	
	private final String query;
	//Provided by app
	private final String location_lat;
	private final String location_long;
	
	
	private final static String YELP_HOST = "api.yelp.com";
	private final static int SEARCH_LIMIT = 3;
	private final static String YELP_SEARCH_PATH = "/v2/search";
	private final static String BUSINESS_PATH = "/v2/business";
	private final static int SORT = 0; // 0=Best Match, 1=Distance, 2=Highest Rated

	// OAuth credentials
	private final static String CONSUMER_KEY = "_PVagi4P5GG9USKKPC-x3Q";
	private final static String CONSUMER_SECRET = "JG1vJi8Q1tKz6DTqZf1QIgIHkDQ";
	private final static String TOKEN = "TtlyJbS7YDbUQyxUxD3yXvYCfxQTRGt3";
	private final static String TOKEN_SECRET = "pFuB0d6nPnp3fSmJHKrVlCXnuk4";
	
	//all category title to alias json
	private static String categoryJSONList = "[{\"category\": [{\"category\": [], \"alias\": \"amateursportsteams\", \"title\": \"Amateur Sports Teams\"}, {\"category\": [], \"alias\": \"amusementparks\", \"title\": \"Amusement Parks\"}, {\"category\": [], \"alias\": \"aquariums\", \"title\": \"Aquariums\"}, {\"category\": [], \"alias\": \"archery\", \"title\": \"Archery\"}, {\"category\": [], \"alias\": \"beaches\", \"title\": \"Beaches\"}, {\"category\": [], \"alias\": \"bikerentals\", \"title\": \"Bike Rentals\"}, {\"category\": [], \"alias\": \"boating\", \"title\": \"Boating\"}, {\"category\": [], \"alias\": \"bowling\", \"title\": \"Bowling\"}, {\"category\": [], \"alias\": \"climbing\", \"title\": \"Climbing\"}, {\"category\": [], \"alias\": \"discgolf\", \"title\": \"Disc Golf\"}, {\"category\": [{\"category\": [], \"alias\": \"freediving\", \"title\": \"Free Diving\"}, {\"category\": [], \"alias\": \"scuba\", \"title\": \"Scuba Diving\"}], \"alias\": \"diving\", \"title\": \"Diving\"}, {\"category\": [], \"alias\": \"fishing\", \"title\": \"Fishing\"}, {\"category\": [{\"category\": [], \"alias\": \"dancestudio\", \"title\": \"Dance Studios\"}, {\"category\": [], \"alias\": \"gyms\", \"title\": \"Gyms\"}, {\"category\": [], \"alias\": \"martialarts\", \"title\": \"Martial Arts\"}, {\"category\": [], \"alias\": \"pilates\", \"title\": \"Pilates\"}, {\"category\": [], \"alias\": \"swimminglessons\", \"title\": \"Swimming Lessons/Schools\"}, {\"category\": [], \"alias\": \"taichi\", \"title\": \"Tai Chi\"}, {\"category\": [], \"alias\": \"healthtrainers\", \"title\": \"Trainers\"}, {\"category\": [], \"alias\": \"yoga\", \"title\": \"Yoga\"}], \"alias\": \"fitness\", \"title\": \"Fitness & Instruction\"}, {\"category\": [], \"alias\": \"gokarts\", \"title\": \"Go Karts\"}, {\"category\": [], \"alias\": \"golf\", \"title\": \"Golf\"}, {\"category\": [], \"alias\": \"gun_ranges\", \"title\": \"Gun/Rifle Ranges\"}, {\"category\": [], \"alias\": \"hiking\", \"title\": \"Hiking\"}, {\"category\": [], \"alias\": \"horseracing\", \"title\": \"Horse Racing\"}, {\"category\": [], \"alias\": \"horsebackriding\", \"title\": \"Horseback Riding\"}, {\"category\": [], \"alias\": \"lakes\", \"title\": \"Lakes\"}, {\"category\": [], \"alias\": \"leisure_centers\", \"title\": \"Leisure Centers\"}, {\"category\": [], \"alias\": \"mini_golf\", \"title\": \"Mini Golf\"}, {\"category\": [], \"alias\": \"mountainbiking\", \"title\": \"Mountain Biking\"}, {\"category\": [], \"alias\": \"paintball\", \"title\": \"Paintball\"}, {\"category\": [{\"category\": [], \"alias\": \"dog_parks\", \"title\": \"Dog Parks\"}, {\"category\": [], \"alias\": \"skate_parks\", \"title\": \"Skate Parks\"}], \"alias\": \"parks\", \"title\": \"Parks\"}, {\"category\": [], \"alias\": \"playgrounds\", \"title\": \"Playgrounds\"}, {\"category\": [], \"alias\": \"rafting\", \"title\": \"Rafting/Kayaking\"}, {\"category\": [], \"alias\": \"recreation\", \"title\": \"Recreation Centers\"}, {\"category\": [], \"alias\": \"rock_climbing\", \"title\": \"Rock Climbing\"}, {\"category\": [], \"alias\": \"skatingrinks\", \"title\": \"Skating Rinks\"}, {\"category\": [], \"alias\": \"skydiving\", \"title\": \"Skydiving\"}, {\"category\": [], \"alias\": \"football\", \"title\": \"Soccer\"}, {\"category\": [], \"alias\": \"sports_clubs\", \"title\": \"Sports Clubs\"}, {\"category\": [], \"alias\": \"summer_camps\", \"title\": \"Summer Camps\"}, {\"category\": [], \"alias\": \"surfing\", \"title\": \"Surfing\"}, {\"category\": [], \"alias\": \"swimmingpools\", \"title\": \"Swimming Pools\"}, {\"category\": [], \"alias\": \"tennis\", \"title\": \"Tennis\"}, {\"category\": [], \"alias\": \"zoos\", \"title\": \"Zoos\"}], \"alias\": \"active\", \"title\": \"Active Life\"}, {\"category\": [{\"category\": [], \"alias\": \"arcades\", \"title\": \"Arcades\"}, {\"category\": [], \"alias\": \"galleries\", \"title\": \"Art Galleries\"}, {\"category\": [], \"alias\": \"gardens\", \"title\": \"Botanical Gardens\"}, {\"category\": [], \"alias\": \"casinos\", \"title\": \"Casinos\"}, {\"category\": [], \"alias\": \"movietheaters\", \"title\": \"Cinema\"}, {\"category\": [], \"alias\": \"festivals\", \"title\": \"Festivals\"}, {\"category\": [], \"alias\": \"jazzandblues\", \"title\": \"Jazz & Blues\"}, {\"category\": [], \"alias\": \"museums\", \"title\": \"Museums\"}, {\"category\": [], \"alias\": \"musicvenues\", \"title\": \"Music Venues\"}, {\"category\": [], \"alias\": \"opera\", \"title\": \"Opera & Ballet\"}, {\"category\": [], \"alias\": \"theater\", \"title\": \"Performing Arts\"}, {\"category\": [], \"alias\": \"sportsteams\", \"title\": \"Professional Sports Teams\"}, {\"category\": [], \"alias\": \"psychic_astrology\", \"title\": \"Psychics & Astrologers\"}, {\"category\": [], \"alias\": \"social_clubs\", \"title\": \"Social Clubs\"}, {\"category\": [], \"alias\": \"stadiumsarenas\", \"title\": \"Stadiums & Arenas\"}, {\"category\": [], \"alias\": \"wineries\", \"title\": \"Wineries\"}], \"alias\": \"arts\", \"title\": \"Arts & Entertainment\"}, {\"category\": [{\"category\": [], \"alias\": \"auto_detailing\", \"title\": \"Auto Detailing\"}, {\"category\": [], \"alias\": \"autoglass\", \"title\": \"Auto Glass Services\"}, {\"category\": [], \"alias\": \"autopartssupplies\", \"title\": \"Auto Parts & Supplies\"}, {\"category\": [], \"alias\": \"autorepair\", \"title\": \"Auto Repair\"}, {\"category\": [], \"alias\": \"bodyshops\", \"title\": \"Body Shops\"}, {\"category\": [], \"alias\": \"car_dealers\", \"title\": \"Car Dealers\"}, {\"category\": [], \"alias\": \"stereo_installation\", \"title\": \"Car Stereo Installation\"}, {\"category\": [], \"alias\": \"carwash\", \"title\": \"Car Wash\"}, {\"category\": [], \"alias\": \"servicestations\", \"title\": \"Gas & Service Stations\"}, {\"category\": [], \"alias\": \"motorcycledealers\", \"title\": \"Motorcycle Dealers\"}, {\"category\": [], \"alias\": \"motorcyclerepair\", \"title\": \"Motorcycle Repair\"}, {\"category\": [], \"alias\": \"oilchange\", \"title\": \"Oil Change Stations\"}, {\"category\": [], \"alias\": \"parking\", \"title\": \"Parking\"}, {\"category\": [], \"alias\": \"rv_dealers\", \"title\": \"RV Dealers\"}, {\"category\": [], \"alias\": \"smog_check_stations\", \"title\": \"Smog Check Stations\"}, {\"category\": [], \"alias\": \"tires\", \"title\": \"Tires\"}, {\"category\": [], \"alias\": \"towing\", \"title\": \"Towing\"}, {\"category\": [], \"alias\": \"truck_rental\", \"title\": \"Truck Rental\"}, {\"category\": [], \"alias\": \"windshieldinstallrepair\", \"title\": \"Windshield Installation & Repair\"}], \"alias\": \"auto\", \"title\": \"Automotive\"}, {\"category\": [{\"category\": [], \"alias\": \"barbers\", \"title\": \"Barbers\"}, {\"category\": [], \"alias\": \"cosmetics\", \"title\": \"Cosmetics & Beauty Supply\"}, {\"category\": [], \"alias\": \"spas\", \"title\": \"Day Spas\"}, {\"category\": [], \"alias\": \"eyelashservice\", \"title\": \"Eyelash Service\"}, {\"category\": [], \"alias\": \"hair_extensions\", \"title\": \"Hair Extensions\"}, {\"category\": [{\"category\": [], \"alias\": \"laser_hair_removal\", \"title\": \"Laser Hair Removal\"}], \"alias\": \"hairremoval\", \"title\": \"Hair Removal\"}, {\"category\": [{\"category\": [], \"alias\": \"hair_extensions\", \"title\": \"Hair Extensions\"}], \"alias\": \"hair\", \"title\": \"Hair Salons\"}, {\"category\": [], \"alias\": \"makeupartists\", \"title\": \"Makeup Artists\"}, {\"category\": [], \"alias\": \"massage\", \"title\": \"Massage\"}, {\"category\": [], \"alias\": \"medicalspa\", \"title\": \"Medical Spas\"}, {\"category\": [], \"alias\": \"othersalons\", \"title\": \"Nail Salons\"}, {\"category\": [], \"alias\": \"piercing\", \"title\": \"Piercing\"}, {\"category\": [], \"alias\": \"rolfing\", \"title\": \"Rolfing\"}, {\"category\": [], \"alias\": \"skincare\", \"title\": \"Skin Care\"}, {\"category\": [], \"alias\": \"tanning\", \"title\": \"Tanning\"}, {\"category\": [], \"alias\": \"tattoo\", \"title\": \"Tattoo\"}], \"alias\": \"beautysvc\", \"title\": \"Beauty and Spas\"}, {\"category\": [{\"category\": [], \"alias\": \"adultedu\", \"title\": \"Adult Education\"}, {\"category\": [], \"alias\": \"collegeuniv\", \"title\": \"Colleges & Universities\"}, {\"category\": [], \"alias\": \"educationservices\", \"title\": \"Educational Services\"}, {\"category\": [], \"alias\": \"elementaryschools\", \"title\": \"Elementary Schools\"}, {\"category\": [], \"alias\": \"highschools\", \"title\": \"Middle Schools & High Schools\"}, {\"category\": [], \"alias\": \"preschools\", \"title\": \"Preschools\"}, {\"category\": [], \"alias\": \"privatetutors\", \"title\": \"Private Tutors\"}, {\"category\": [], \"alias\": \"specialed\", \"title\": \"Special Education\"}, {\"category\": [{\"category\": [], \"alias\": \"artschools\", \"title\": \"Art Schools\"}, {\"category\": [], \"alias\": \"cookingschools\", \"title\": \"Cooking Schools\"}, {\"category\": [], \"alias\": \"cosmetology_schools\", \"title\": \"Cosmetology Schools\"}, {\"category\": [], \"alias\": \"dance_schools\", \"title\": \"Dance Schools\"}, {\"category\": [], \"alias\": \"driving_schools\", \"title\": \"Driving Schools\"}, {\"category\": [], \"alias\": \"flightinstruction\", \"title\": \"Flight Instruction\"}, {\"category\": [], \"alias\": \"language_schools\", \"title\": \"Language Schools\"}, {\"category\": [], \"alias\": \"massage_schools\", \"title\": \"Massage Schools\"}, {\"category\": [], \"alias\": \"swimminglessons\", \"title\": \"Swimming Lessons/Schools\"}, {\"category\": [], \"alias\": \"vocation\", \"title\": \"Vocational and Technical School\"}], \"alias\": \"specialtyschools\", \"title\": \"Specialty Schools\"}, {\"category\": [], \"alias\": \"testprep\", \"title\": \"Test Preparation\"}, {\"category\": [], \"alias\": \"tutoring\", \"title\": \"Tutoring Centers\"}], \"alias\": \"education\", \"title\": \"Education\"}, {\"category\": [{\"category\": [], \"alias\": \"boatcharters\", \"title\": \"Boat Charters\"}, {\"category\": [], \"alias\": \"stationery\", \"title\": \"Cards & Stationery\"}, {\"category\": [], \"alias\": \"catering\", \"title\": \"Caterers\"}, {\"category\": [], \"alias\": \"clowns\", \"title\": \"Clowns\"}, {\"category\": [], \"alias\": \"djs\", \"title\": \"DJs\"}, {\"category\": [], \"alias\": \"hotels\", \"title\": \"Hotels\"}, {\"category\": [], \"alias\": \"magicians\", \"title\": \"Magicians\"}, {\"category\": [], \"alias\": \"officiants\", \"title\": \"Officiants\"}, {\"category\": [], \"alias\": \"eventplanning\", \"title\": \"Party & Event Planning\"}, {\"category\": [], \"alias\": \"partysupplies\", \"title\": \"Party Supplies\"}, {\"category\": [], \"alias\": \"personalchefs\", \"title\": \"Personal Chefs\"}, {\"category\": [], \"alias\": \"photographers\", \"title\": \"Photographers\"}, {\"category\": [], \"alias\": \"venues\", \"title\": \"Venues & Event Spaces\"}, {\"category\": [], \"alias\": \"videographers\", \"title\": \"Videographers\"}, {\"category\": [], \"alias\": \"wedding_planning\", \"title\": \"Wedding Planning\"}], \"alias\": \"eventservices\", \"title\": \"Event Planning & Services\"}, {\"category\": [{\"category\": [], \"alias\": \"banks\", \"title\": \"Banks & Credit Unions\"}, {\"category\": [], \"alias\": \"paydayloans\", \"title\": \"Check Cashing/Pay-day Loans\"}, {\"category\": [], \"alias\": \"financialadvising\", \"title\": \"Financial Advising\"}, {\"category\": [], \"alias\": \"insurance\", \"title\": \"Insurance\"}, {\"category\": [], \"alias\": \"investing\", \"title\": \"Investing\"}, {\"category\": [], \"alias\": \"taxservices\", \"title\": \"Tax Services\"}], \"alias\": \"financialservices\", \"title\": \"Financial Services\"}, {\"category\": [{\"category\": [], \"alias\": \"bagels\", \"title\": \"Bagels\"}, {\"category\": [], \"alias\": \"bakeries\", \"title\": \"Bakeries\"}, {\"category\": [], \"alias\": \"beer_and_wine\", \"title\": \"Beer, Wine & Spirits\"}, {\"category\": [], \"alias\": \"breweries\", \"title\": \"Breweries\"}, {\"category\": [], \"alias\": \"butcher\", \"title\": \"Butcher\"}, {\"category\": [], \"alias\": \"coffee\", \"title\": \"Coffee & Tea\"}, {\"category\": [], \"alias\": \"convenience\", \"title\": \"Convenience Stores\"}, {\"category\": [], \"alias\": \"desserts\", \"title\": \"Desserts\"}, {\"category\": [], \"alias\": \"diyfood\", \"title\": \"Do-It-Yourself Food\"}, {\"category\": [], \"alias\": \"donuts\", \"title\": \"Donuts\"}, {\"category\": [], \"alias\": \"farmersmarket\", \"title\": \"Farmers Market\"}, {\"category\": [], \"alias\": \"fooddeliveryservices\", \"title\": \"Food Delivery Services\"}, {\"category\": [], \"alias\": \"grocery\", \"title\": \"Grocery\"}, {\"category\": [], \"alias\": \"icecream\", \"title\": \"Ice Cream & Frozen Yogurt\"}, {\"category\": [], \"alias\": \"internetcafe\", \"title\": \"Internet Cafes\"}, {\"category\": [], \"alias\": \"juicebars\", \"title\": \"Juice Bars & Smoothies\"}, {\"category\": [{\"category\": [], \"alias\": \"candy\", \"title\": \"Candy Stores\"}, {\"category\": [], \"alias\": \"cheese\", \"title\": \"Cheese Shops\"}, {\"category\": [], \"alias\": \"chocolate\", \"title\": \"Chocolatiers and Shops\"}, {\"category\": [], \"alias\": \"ethnicmarkets\", \"title\": \"Ethnic Food\"}, {\"category\": [], \"alias\": \"markets\", \"title\": \"Fruits & Veggies\"}, {\"category\": [], \"alias\": \"healthmarkets\", \"title\": \"Health Markets\"}, {\"category\": [], \"alias\": \"herbsandspices\", \"title\": \"Herbs and Spices\"}, {\"category\": [], \"alias\": \"meats\", \"title\": \"Meat Shops\"}, {\"category\": [], \"alias\": \"seafoodmarkets\", \"title\": \"Seafood Markets\"}], \"alias\": \"gourmet\", \"title\": \"Specialty Food\"}, {\"category\": [], \"alias\": \"streetvendors\", \"title\": \"Street Vendors\"}, {\"category\": [], \"alias\": \"tea\", \"title\": \"Tea Rooms\"}, {\"category\": [], \"alias\": \"wineries\", \"title\": \"Wineries\"}], \"alias\": \"food\", \"title\": \"Food\"}, {\"category\": [{\"category\": [], \"alias\": \"acupuncture\", \"title\": \"Acupuncture\"}, {\"category\": [], \"alias\": \"cannabis_clinics\", \"title\": \"Cannabis Clinics\"}, {\"category\": [], \"alias\": \"chiropractors\", \"title\": \"Chiropractors\"}, {\"category\": [], \"alias\": \"c_and_mh\", \"title\": \"Counseling & Mental Health\"}, {\"category\": [{\"category\": [], \"alias\": \"cosmeticdentists\", \"title\": \"Cosmetic Dentists\"}, {\"category\": [], \"alias\": \"endodontists\", \"title\": \"Endodontists\"}, {\"category\": [], \"alias\": \"generaldentistry\", \"title\": \"General Dentistry\"}, {\"category\": [], \"alias\": \"oralsurgeons\", \"title\": \"Oral Surgeons\"}, {\"category\": [], \"alias\": \"orthodontists\", \"title\": \"Orthodontists\"}, {\"category\": [], \"alias\": \"pediatric_dentists\", \"title\": \"Pediatric Dentists\"}, {\"category\": [], \"alias\": \"periodontists\", \"title\": \"Periodontists\"}], \"alias\": \"dentists\", \"title\": \"Dentists\"}, {\"category\": [{\"category\": [], \"alias\": \"allergist\", \"title\": \"Allergists\"}, {\"category\": [], \"alias\": \"audiologist\", \"title\": \"Audiologist\"}, {\"category\": [], \"alias\": \"cardiology\", \"title\": \"Cardiologists\"}, {\"category\": [], \"alias\": \"cosmeticsurgeons\", \"title\": \"Cosmetic Surgeons\"}, {\"category\": [], \"alias\": \"dermatology\", \"title\": \"Dermatologists\"}, {\"category\": [], \"alias\": \"earnosethroat\", \"title\": \"Ear Nose & Throat\"}, {\"category\": [], \"alias\": \"familydr\", \"title\": \"Family Practice\"}, {\"category\": [], \"alias\": \"fertility\", \"title\": \"Fertility\"}, {\"category\": [], \"alias\": \"gastroenterologist\", \"title\": \"Gastroenterologist\"}, {\"category\": [], \"alias\": \"gerontologist\", \"title\": \"Gerontologists\"}, {\"category\": [], \"alias\": \"internalmed\", \"title\": \"Internal Medicine\"}, {\"category\": [], \"alias\": \"naturopathic\", \"title\": \"Naturopathic/Holistic\"}, {\"category\": [], \"alias\": \"neurologist\", \"title\": \"Neurologist\"}, {\"category\": [], \"alias\": \"obgyn\", \"title\": \"Obstetricians and Gynecologists\"}, {\"category\": [], \"alias\": \"oncologist\", \"title\": \"Oncologist\"}, {\"category\": [], \"alias\": \"opthamalogists\", \"title\": \"Ophthalmologists\"}, {\"category\": [], \"alias\": \"orthopedists\", \"title\": \"Orthopedists\"}, {\"category\": [], \"alias\": \"osteopathicphysicians\", \"title\": \"Osteopathic Physicians\"}, {\"category\": [], \"alias\": \"pediatricians\", \"title\": \"Pediatricians\"}, {\"category\": [], \"alias\": \"podiatrists\", \"title\": \"Podiatrists\"}, {\"category\": [], \"alias\": \"proctologist\", \"title\": \"Proctologists\"}, {\"category\": [], \"alias\": \"psychiatrists\", \"title\": \"Psychiatrists\"}, {\"category\": [], \"alias\": \"pulmonologist\", \"title\": \"Pulmonologist\"}, {\"category\": [], \"alias\": \"sportsmed\", \"title\": \"Sports Medicine\"}, {\"category\": [], \"alias\": \"tattooremoval\", \"title\": \"Tattoo Removal\"}], \"alias\": \"physicians\", \"title\": \"Doctors\"}, {\"category\": [], \"alias\": \"homehealthcare\", \"title\": \"Home Health Care\"}, {\"category\": [], \"alias\": \"hospice\", \"title\": \"Hospice\"}, {\"category\": [], \"alias\": \"hospitals\", \"title\": \"Hospitals\"}, {\"category\": [], \"alias\": \"laserlasikeyes\", \"title\": \"Laser Eye Surgery/Lasik\"}, {\"category\": [], \"alias\": \"massage_therapy\", \"title\": \"Massage Therapy\"}, {\"category\": [], \"alias\": \"medcenters\", \"title\": \"Medical Centers\"}, {\"category\": [], \"alias\": \"medicalspa\", \"title\": \"Medical Spas\"}, {\"category\": [], \"alias\": \"midwives\", \"title\": \"Midwives\"}, {\"category\": [], \"alias\": \"nutritionists\", \"title\": \"Nutritionists\"}, {\"category\": [], \"alias\": \"optometrists\", \"title\": \"Optometrists\"}, {\"category\": [], \"alias\": \"physicaltherapy\", \"title\": \"Physical Therapy\"}, {\"category\": [], \"alias\": \"reflexology\", \"title\": \"Reflexology\"}, {\"category\": [], \"alias\": \"rehabilitation_center\", \"title\": \"Rehabilitation Center\"}, {\"category\": [], \"alias\": \"retirement_homes\", \"title\": \"Retirement Homes\"}, {\"category\": [], \"alias\": \"speech_therapists\", \"title\": \"Speech Therapists\"}, {\"category\": [], \"alias\": \"tcm\", \"title\": \"Traditional Chinese Medicine\"}, {\"category\": [], \"alias\": \"urgent_care\", \"title\": \"Urgent Care\"}, {\"category\": [], \"alias\": \"weightlosscenters\", \"title\": \"Weight Loss Centers\"}], \"alias\": \"health\", \"title\": \"Health and Medical\"}, {\"category\": [{\"category\": [], \"alias\": \"buildingsupplies\", \"title\": \"Building Supplies\"}, {\"category\": [], \"alias\": \"carpetinstallation\", \"title\": \"Carpet Installation\"}, {\"category\": [], \"alias\": \"carpeting\", \"title\": \"Carpeting\"}, {\"category\": [], \"alias\": \"contractors\", \"title\": \"Contractors\"}, {\"category\": [], \"alias\": \"electricians\", \"title\": \"Electricians\"}, {\"category\": [], \"alias\": \"flooring\", \"title\": \"Flooring\"}, {\"category\": [], \"alias\": \"garage_door_services\", \"title\": \"Garage Door Services\"}, {\"category\": [], \"alias\": \"gardeners\", \"title\": \"Gardeners\"}, {\"category\": [], \"alias\": \"handyman\", \"title\": \"Handyman\"}, {\"category\": [], \"alias\": \"hvac\", \"title\": \"Heating & Air Conditioning/HVAC\"}, {\"category\": [], \"alias\": \"homecleaning\", \"title\": \"Home Cleaning\"}, {\"category\": [], \"alias\": \"home_inspectors\", \"title\": \"Home Inspectors\"}, {\"category\": [], \"alias\": \"home_organization\", \"title\": \"Home Organization\"}, {\"category\": [], \"alias\": \"hometheatreinstallation\", \"title\": \"Home Theatre Installation\"}, {\"category\": [], \"alias\": \"interiordesign\", \"title\": \"Interior Design\"}, {\"category\": [], \"alias\": \"isps\", \"title\": \"Internet Service Providers\"}, {\"category\": [], \"alias\": \"locksmiths\", \"title\": \"Keys & Locksmiths\"}, {\"category\": [], \"alias\": \"landscapearchitects\", \"title\": \"Landscape Architects\"}, {\"category\": [], \"alias\": \"landscaping\", \"title\": \"Landscaping\"}, {\"category\": [], \"alias\": \"lighting\", \"title\": \"Lighting Fixtures & Equipment\"}, {\"category\": [], \"alias\": \"masonry_concrete\", \"title\": \"Masonry/Concrete\"}, {\"category\": [], \"alias\": \"movers\", \"title\": \"Movers\"}, {\"category\": [], \"alias\": \"painters\", \"title\": \"Painters\"}, {\"category\": [], \"alias\": \"plumbing\", \"title\": \"Plumbing\"}, {\"category\": [], \"alias\": \"poolcleaners\", \"title\": \"Pool Cleaners\"}, {\"category\": [{\"category\": [], \"alias\": \"apartments\", \"title\": \"Apartments\"}, {\"category\": [], \"alias\": \"homestaging\", \"title\": \"Home Staging\"}, {\"category\": [], \"alias\": \"mortgagebrokers\", \"title\": \"Mortgage Brokers\"}, {\"category\": [], \"alias\": \"propertymgmt\", \"title\": \"Property Management\"}, {\"category\": [], \"alias\": \"realestateagents\", \"title\": \"Real Estate Agents\"}, {\"category\": [], \"alias\": \"realestatesvcs\", \"title\": \"Real Estate Services\"}, {\"category\": [], \"alias\": \"university_housing\", \"title\": \"University Housing\"}], \"alias\": \"realestate\", \"title\": \"Real Estate\"}, {\"category\": [], \"alias\": \"roofing\", \"title\": \"Roofing\"}, {\"category\": [], \"alias\": \"securitysystems\", \"title\": \"Security Systems\"}, {\"category\": [], \"alias\": \"blinds\", \"title\": \"Shades & Blinds\"}, {\"category\": [], \"alias\": \"solarinstallation\", \"title\": \"Solar Installation\"}, {\"category\": [], \"alias\": \"televisionserviceproviders\", \"title\": \"Television Service Providers\"}, {\"category\": [], \"alias\": \"treeservices\", \"title\": \"Tree Services\"}, {\"category\": [], \"alias\": \"windowwashing\", \"title\": \"Window Washing\"}, {\"category\": [], \"alias\": \"windowsinstallation\", \"title\": \"Windows Installation\"}], \"alias\": \"homeservices\", \"title\": \"Home Services\"}, {\"category\": [{\"category\": [], \"alias\": \"airports\", \"title\": \"Airports\"}, {\"category\": [], \"alias\": \"bedbreakfast\", \"title\": \"Bed & Breakfast\"}, {\"category\": [], \"alias\": \"campgrounds\", \"title\": \"Campgrounds\"}, {\"category\": [], \"alias\": \"carrental\", \"title\": \"Car Rental\"}, {\"category\": [], \"alias\": \"guesthouses\", \"title\": \"Guest Houses\"}, {\"category\": [], \"alias\": \"hostels\", \"title\": \"Hostels\"}, {\"category\": [], \"alias\": \"hotels\", \"title\": \"Hotels\"}, {\"category\": [], \"alias\": \"motorcycle_rental\", \"title\": \"Motorcycle Rental\"}, {\"category\": [], \"alias\": \"rvrental\", \"title\": \"RV Rental\"}, {\"category\": [], \"alias\": \"skiresorts\", \"title\": \"Ski Resorts\"}, {\"category\": [], \"alias\": \"tours\", \"title\": \"Tours\"}, {\"category\": [{\"category\": [], \"alias\": \"airlines\", \"title\": \"Airlines\"}, {\"category\": [], \"alias\": \"airport_shuttles\", \"title\": \"Airport Shuttles\"}, {\"category\": [], \"alias\": \"limos\", \"title\": \"Limos\"}, {\"category\": [], \"alias\": \"publictransport\", \"title\": \"Public Transportation\"}, {\"category\": [], \"alias\": \"taxis\", \"title\": \"Taxis\"}], \"alias\": \"transport\", \"title\": \"Transportation\"}, {\"category\": [], \"alias\": \"travelservices\", \"title\": \"Travel Services\"}, {\"category\": [], \"alias\": \"vacationrentalagents\", \"title\": \"Vacation Rental Agents\"}, {\"category\": [], \"alias\": \"vacation_rentals\", \"title\": \"Vacation Rentals\"}], \"alias\": \"hotelstravel\", \"title\": \"Hotels & Travel\"}, {\"category\": [], \"alias\": \"localflavor\", \"title\": \"Local Flavor\"}, {\"category\": [{\"category\": [], \"alias\": \"homeappliancerepair\", \"title\": \"Appliances & Repair\"}, {\"category\": [], \"alias\": \"bailbondsmen\", \"title\": \"Bail Bondsmen\"}, {\"category\": [], \"alias\": \"bike_repair_maintenance\", \"title\": \"Bike Repair/Maintenance\"}, {\"category\": [], \"alias\": \"carpet_cleaning\", \"title\": \"Carpet Cleaning\"}, {\"category\": [], \"alias\": \"childcare\", \"title\": \"Child Care & Day Care\"}, {\"category\": [], \"alias\": \"nonprofit\", \"title\": \"Community Service/Non-Profit\"}, {\"category\": [], \"alias\": \"couriers\", \"title\": \"Couriers & Delivery Services\"}, {\"category\": [], \"alias\": \"drycleaninglaundry\", \"title\": \"Dry Cleaning & Laundry\"}, {\"category\": [], \"alias\": \"electronicsrepair\", \"title\": \"Electronics Repair\"}, {\"category\": [], \"alias\": \"funeralservices\", \"title\": \"Funeral Services & Cemeteries\"}, {\"category\": [], \"alias\": \"reupholstery\", \"title\": \"Furniture Reupholstery\"}, {\"category\": [], \"alias\": \"itservices\", \"title\": \"IT Services & Computer Repair\"}, {\"category\": [], \"alias\": \"junkremovalandhauling\", \"title\": \"Junk Removal and Hauling\"}, {\"category\": [], \"alias\": \"notaries\", \"title\": \"Notaries\"}, {\"category\": [], \"alias\": \"pest_control\", \"title\": \"Pest Control\"}, {\"category\": [], \"alias\": \"copyshops\", \"title\": \"Printing Services\"}, {\"category\": [], \"alias\": \"recording_studios\", \"title\": \"Recording & Rehearsal Studios\"}, {\"category\": [], \"alias\": \"recyclingcenter\", \"title\": \"Recycling Center\"}, {\"category\": [], \"alias\": \"screenprinting\", \"title\": \"Screen Printing\"}, {\"category\": [], \"alias\": \"screen_printing_tshirt_printing\", \"title\": \"Screen Printing/T-Shirt Printing\"}, {\"category\": [], \"alias\": \"selfstorage\", \"title\": \"Self Storage\"}, {\"category\": [], \"alias\": \"sewingalterations\", \"title\": \"Sewing & Alterations\"}, {\"category\": [], \"alias\": \"shipping_centers\", \"title\": \"Shipping Centers\"}, {\"category\": [], \"alias\": \"shoerepair\", \"title\": \"Shoe Repair\"}, {\"category\": [], \"alias\": \"snowremoval\", \"title\": \"Snow Removal\"}, {\"category\": [], \"alias\": \"watch_repair\", \"title\": \"Watch Repair\"}], \"alias\": \"localservices\", \"title\": \"Local Services\"}, {\"category\": [{\"category\": [], \"alias\": \"printmedia\", \"title\": \"Print Media\"}, {\"category\": [], \"alias\": \"radiostations\", \"title\": \"Radio Stations\"}, {\"category\": [], \"alias\": \"televisionstations\", \"title\": \"Television Stations\"}], \"alias\": \"massmedia\", \"title\": \"Mass Media\"}, {\"category\": [{\"category\": [], \"alias\": \"adultentertainment\", \"title\": \"Adult Entertainment\"}, {\"category\": [{\"category\": [], \"alias\": \"champagne_bars\", \"title\": \"Champagne Bars\"}, {\"category\": [], \"alias\": \"divebars\", \"title\": \"Dive Bars\"}, {\"category\": [], \"alias\": \"gaybars\", \"title\": \"Gay Bars\"}, {\"category\": [], \"alias\": \"hookah_bars\", \"title\": \"Hookah Bars\"}, {\"category\": [], \"alias\": \"lounges\", \"title\": \"Lounges\"}, {\"category\": [], \"alias\": \"pubs\", \"title\": \"Pubs\"}, {\"category\": [], \"alias\": \"sportsbars\", \"title\": \"Sports Bars\"}, {\"category\": [], \"alias\": \"wine_bars\", \"title\": \"Wine Bars\"}], \"alias\": \"bars\", \"title\": \"Bars\"}, {\"category\": [], \"alias\": \"comedyclubs\", \"title\": \"Comedy Clubs\"}, {\"category\": [], \"alias\": \"danceclubs\", \"title\": \"Dance Clubs\"}, {\"category\": [], \"alias\": \"jazzandblues\", \"title\": \"Jazz & Blues\"}, {\"category\": [], \"alias\": \"karaoke\", \"title\": \"Karaoke\"}, {\"category\": [], \"alias\": \"musicvenues\", \"title\": \"Music Venues\"}, {\"category\": [], \"alias\": \"poolhalls\", \"title\": \"Pool Halls\"}], \"alias\": \"nightlife\", \"title\": \"Nightlife\"}, {\"category\": [{\"category\": [], \"alias\": \"animalshelters\", \"title\": \"Animal Shelters\"}, {\"category\": [], \"alias\": \"horse_boarding\", \"title\": \"Horse Boarding\"}, {\"category\": [{\"category\": [], \"alias\": \"dogwalkers\", \"title\": \"Dog Walkers\"}, {\"category\": [], \"alias\": \"pet_sitting\", \"title\": \"Pet Boarding/Pet Sitting\"}, {\"category\": [], \"alias\": \"groomer\", \"title\": \"Pet Groomers\"}, {\"category\": [], \"alias\": \"pet_training\", \"title\": \"Pet Training\"}], \"alias\": \"petservices\", \"title\": \"Pet Services\"}, {\"category\": [], \"alias\": \"petstore\", \"title\": \"Pet Stores\"}, {\"category\": [], \"alias\": \"vet\", \"title\": \"Veterinarians\"}], \"alias\": \"pets\", \"title\": \"Pets\"}, {\"category\": [{\"category\": [], \"alias\": \"accountants\", \"title\": \"Accountants\"}, {\"category\": [], \"alias\": \"advertising\", \"title\": \"Advertising\"}, {\"category\": [], \"alias\": \"architects\", \"title\": \"Architects\"}, {\"category\": [], \"alias\": \"boatrepair\", \"title\": \"Boat Repair\"}, {\"category\": [], \"alias\": \"careercounseling\", \"title\": \"Career Counseling\"}, {\"category\": [], \"alias\": \"employmentagencies\", \"title\": \"Employment Agencies\"}, {\"category\": [], \"alias\": \"graphicdesign\", \"title\": \"Graphic Design\"}, {\"category\": [], \"alias\": \"isps\", \"title\": \"Internet Service Providers\"}, {\"category\": [{\"category\": [], \"alias\": \"bankruptcy\", \"title\": \"Bankruptcy Law\"}, {\"category\": [], \"alias\": \"criminaldefense\", \"title\": \"Criminal Defense Law\"}, {\"category\": [], \"alias\": \"divorce\", \"title\": \"Divorce and Family Law\"}, {\"category\": [], \"alias\": \"employmentlawyers\", \"title\": \"Employment Law\"}, {\"category\": [], \"alias\": \"estateplanning\", \"title\": \"Estate Planning Law\"}, {\"category\": [], \"alias\": \"general_litigation\", \"title\": \"General Litigation\"}, {\"category\": [], \"alias\": \"immigrationlawyers\", \"title\": \"Immigration Law\"}, {\"category\": [], \"alias\": \"personal_injury\", \"title\": \"Personal Injury Law\"}, {\"category\": [], \"alias\": \"realestatelawyers\", \"title\": \"Real Estate Law\"}], \"alias\": \"lawyers\", \"title\": \"Lawyers\"}, {\"category\": [], \"alias\": \"lifecoach\", \"title\": \"Life Coach\"}, {\"category\": [], \"alias\": \"marketing\", \"title\": \"Marketing\"}, {\"category\": [], \"alias\": \"officecleaning\", \"title\": \"Office Cleaning\"}, {\"category\": [], \"alias\": \"privateinvestigation\", \"title\": \"Private Investigation\"}, {\"category\": [], \"alias\": \"publicrelations\", \"title\": \"Public Relations\"}, {\"category\": [], \"alias\": \"videofilmproductions\", \"title\": \"Video/Film Production\"}, {\"category\": [], \"alias\": \"web_design\", \"title\": \"Web Design\"}], \"alias\": \"professional\", \"title\": \"Professional Services\"}, {\"category\": [{\"category\": [], \"alias\": \"departmentsofmotorvehicles\", \"title\": \"Departments of Motor Vehicles\"}, {\"category\": [], \"alias\": \"landmarks\", \"title\": \"Landmarks & Historical Buildings\"}, {\"category\": [], \"alias\": \"libraries\", \"title\": \"Libraries\"}, {\"category\": [], \"alias\": \"policedepartments\", \"title\": \"Police Departments\"}, {\"category\": [], \"alias\": \"postoffices\", \"title\": \"Post Offices\"}], \"alias\": \"publicservicesgovt\", \"title\": \"Public Services & Government\"}, {\"category\": [{\"category\": [], \"alias\": \"apartments\", \"title\": \"Apartments\"}, {\"category\": [], \"alias\": \"homestaging\", \"title\": \"Home Staging\"}, {\"category\": [], \"alias\": \"mortgagebrokers\", \"title\": \"Mortgage Brokers\"}, {\"category\": [], \"alias\": \"propertymgmt\", \"title\": \"Property Management\"}, {\"category\": [], \"alias\": \"realestateagents\", \"title\": \"Real Estate Agents\"}, {\"category\": [], \"alias\": \"realestatesvcs\", \"title\": \"Real Estate Services\"}, {\"category\": [], \"alias\": \"university_housing\", \"title\": \"University Housing\"}], \"alias\": \"realestate\", \"title\": \"Real Estate\"}, {\"category\": [{\"category\": [], \"alias\": \"buddhist_temples\", \"title\": \"Buddhist Temples\"}, {\"category\": [], \"alias\": \"churches\", \"title\": \"Churches\"}, {\"category\": [], \"alias\": \"hindu_temples\", \"title\": \"Hindu Temples\"}, {\"category\": [], \"alias\": \"mosques\", \"title\": \"Mosques\"}, {\"category\": [], \"alias\": \"synagogues\", \"title\": \"Synagogues\"}], \"alias\": \"religiousorgs\", \"title\": \"Religious Organizations\"}, {\"category\": [{\"category\": [], \"alias\": \"afghani\", \"title\": \"Afghan\"}, {\"category\": [], \"alias\": \"african\", \"title\": \"African\"}, {\"category\": [], \"alias\": \"newamerican\", \"title\": \"American (New)\"}, {\"category\": [], \"alias\": \"tradamerican\", \"title\": \"American (Traditional)\"}, {\"category\": [], \"alias\": \"argentine\", \"title\": \"Argentine\"}, {\"category\": [], \"alias\": \"asianfusion\", \"title\": \"Asian Fusion\"}, {\"category\": [], \"alias\": \"bbq\", \"title\": \"Barbeque\"}, {\"category\": [], \"alias\": \"basque\", \"title\": \"Basque\"}, {\"category\": [], \"alias\": \"belgian\", \"title\": \"Belgian\"}, {\"category\": [], \"alias\": \"brasseries\", \"title\": \"Brasseries\"}, {\"category\": [], \"alias\": \"brazilian\", \"title\": \"Brazilian\"}, {\"category\": [], \"alias\": \"breakfast_brunch\", \"title\": \"Breakfast & Brunch\"}, {\"category\": [], \"alias\": \"british\", \"title\": \"British\"}, {\"category\": [], \"alias\": \"buffets\", \"title\": \"Buffets\"}, {\"category\": [], \"alias\": \"burgers\", \"title\": \"Burgers\"}, {\"category\": [], \"alias\": \"burmese\", \"title\": \"Burmese\"}, {\"category\": [], \"alias\": \"cafes\", \"title\": \"Cafes\"}, {\"category\": [], \"alias\": \"cajun\", \"title\": \"Cajun/Creole\"}, {\"category\": [], \"alias\": \"cambodian\", \"title\": \"Cambodian\"}, {\"category\": [], \"alias\": \"caribbean\", \"title\": \"Caribbean\"}, {\"category\": [], \"alias\": \"cheesesteaks\", \"title\": \"Cheesesteaks\"}, {\"category\": [], \"alias\": \"chicken_wings\", \"title\": \"Chicken Wings\"}, {\"category\": [{\"category\": [], \"alias\": \"dimsum\", \"title\": \"Dim Sum\"}, {\"category\": [], \"alias\": \"szechuan\", \"title\": \"Szechuan\"}], \"alias\": \"chinese\", \"title\": \"Chinese\"}, {\"category\": [], \"alias\": \"creperies\", \"title\": \"Creperies\"}, {\"category\": [], \"alias\": \"cuban\", \"title\": \"Cuban\"}, {\"category\": [], \"alias\": \"delis\", \"title\": \"Delis\"}, {\"category\": [], \"alias\": \"diners\", \"title\": \"Diners\"}, {\"category\": [], \"alias\": \"ethiopian\", \"title\": \"Ethiopian\"}, {\"category\": [], \"alias\": \"hotdogs\", \"title\": \"Fast Food\"}, {\"category\": [], \"alias\": \"filipino\", \"title\": \"Filipino\"}, {\"category\": [], \"alias\": \"fishnchips\", \"title\": \"Fish & Chips\"}, {\"category\": [], \"alias\": \"fondue\", \"title\": \"Fondue\"}, {\"category\": [], \"alias\": \"foodstands\", \"title\": \"Food Stands\"}, {\"category\": [], \"alias\": \"french\", \"title\": \"French\"}, {\"category\": [], \"alias\": \"gastropubs\", \"title\": \"Gastropubs\"}, {\"category\": [], \"alias\": \"german\", \"title\": \"German\"}, {\"category\": [], \"alias\": \"gluten_free\", \"title\": \"Gluten-Free\"}, {\"category\": [], \"alias\": \"greek\", \"title\": \"Greek\"}, {\"category\": [], \"alias\": \"halal\", \"title\": \"Halal\"}, {\"category\": [], \"alias\": \"hawaiian\", \"title\": \"Hawaiian\"}, {\"category\": [], \"alias\": \"himalayan\", \"title\": \"Himalayan/Nepalese\"}, {\"category\": [], \"alias\": \"hotdog\", \"title\": \"Hot Dogs\"}, {\"category\": [], \"alias\": \"hungarian\", \"title\": \"Hungarian\"}, {\"category\": [], \"alias\": \"indpak\", \"title\": \"Indian\"}, {\"category\": [], \"alias\": \"indonesian\", \"title\": \"Indonesian\"}, {\"category\": [], \"alias\": \"irish\", \"title\": \"Irish\"}, {\"category\": [], \"alias\": \"italian\", \"title\": \"Italian\"}, {\"category\": [], \"alias\": \"japanese\", \"title\": \"Japanese\"}, {\"category\": [], \"alias\": \"korean\", \"title\": \"Korean\"}, {\"category\": [], \"alias\": \"kosher\", \"title\": \"Kosher\"}, {\"category\": [], \"alias\": \"latin\", \"title\": \"Latin American\"}, {\"category\": [], \"alias\": \"raw_food\", \"title\": \"Live/Raw Food\"}, {\"category\": [], \"alias\": \"malaysian\", \"title\": \"Malaysian\"}, {\"category\": [], \"alias\": \"mediterranean\", \"title\": \"Mediterranean\"}, {\"category\": [], \"alias\": \"mexican\", \"title\": \"Mexican\"}, {\"category\": [], \"alias\": \"mideastern\", \"title\": \"Middle Eastern\"}, {\"category\": [], \"alias\": \"modern_european\", \"title\": \"Modern European\"}, {\"category\": [], \"alias\": \"mongolian\", \"title\": \"Mongolian\"}, {\"category\": [], \"alias\": \"moroccan\", \"title\": \"Moroccan\"}, {\"category\": [], \"alias\": \"pakistani\", \"title\": \"Pakistani\"}, {\"category\": [], \"alias\": \"persian\", \"title\": \"Persian/Iranian\"}, {\"category\": [], \"alias\": \"peruvian\", \"title\": \"Peruvian\"}, {\"category\": [], \"alias\": \"pizza\", \"title\": \"Pizza\"}, {\"category\": [], \"alias\": \"polish\", \"title\": \"Polish\"}, {\"category\": [], \"alias\": \"portuguese\", \"title\": \"Portuguese\"}, {\"category\": [], \"alias\": \"russian\", \"title\": \"Russian\"}, {\"category\": [], \"alias\": \"salad\", \"title\": \"Salad\"}, {\"category\": [], \"alias\": \"sandwiches\", \"title\": \"Sandwiches\"}, {\"category\": [], \"alias\": \"scandinavian\", \"title\": \"Scandinavian\"}, {\"category\": [], \"alias\": \"seafood\", \"title\": \"Seafood\"}, {\"category\": [], \"alias\": \"singaporean\", \"title\": \"Singaporean\"}, {\"category\": [], \"alias\": \"soulfood\", \"title\": \"Soul Food\"}, {\"category\": [], \"alias\": \"soup\", \"title\": \"Soup\"}, {\"category\": [], \"alias\": \"southern\", \"title\": \"Southern\"}, {\"category\": [], \"alias\": \"spanish\", \"title\": \"Spanish\"}, {\"category\": [], \"alias\": \"steak\", \"title\": \"Steakhouses\"}, {\"category\": [], \"alias\": \"sushi\", \"title\": \"Sushi Bars\"}, {\"category\": [], \"alias\": \"taiwanese\", \"title\": \"Taiwanese\"}, {\"category\": [], \"alias\": \"tapas\", \"title\": \"Tapas Bars\"}, {\"category\": [], \"alias\": \"tapasmallplates\", \"title\": \"Tapas/Small Plates\"}, {\"category\": [], \"alias\": \"tex-mex\", \"title\": \"Tex-Mex\"}, {\"category\": [], \"alias\": \"thai\", \"title\": \"Thai\"}, {\"category\": [], \"alias\": \"turkish\", \"title\": \"Turkish\"}, {\"category\": [], \"alias\": \"ukrainian\", \"title\": \"Ukrainian\"}, {\"category\": [], \"alias\": \"vegan\", \"title\": \"Vegan\"}, {\"category\": [], \"alias\": \"vegetarian\", \"title\": \"Vegetarian\"}, {\"category\": [], \"alias\": \"vietnamese\", \"title\": \"Vietnamese\"}], \"alias\": \"restaurants\", \"title\": \"Restaurants\"}, {\"category\": [{\"category\": [], \"alias\": \"adult\", \"title\": \"Adult\"}, {\"category\": [], \"alias\": \"antiques\", \"title\": \"Antiques\"}, {\"category\": [], \"alias\": \"galleries\", \"title\": \"Art Galleries\"}, {\"category\": [{\"category\": [], \"alias\": \"artsupplies\", \"title\": \"Art Supplies\"}, {\"category\": [], \"alias\": \"stationery\", \"title\": \"Cards & Stationery\"}, {\"category\": [], \"alias\": \"costumes\", \"title\": \"Costumes\"}, {\"category\": [], \"alias\": \"fabricstores\", \"title\": \"Fabric Stores\"}, {\"category\": [], \"alias\": \"framing\", \"title\": \"Framing\"}], \"alias\": \"artsandcrafts\", \"title\": \"Arts & Crafts\"}, {\"category\": [], \"alias\": \"baby_gear\", \"title\": \"Baby Gear & Furniture\"}, {\"category\": [{\"category\": [], \"alias\": \"bookstores\", \"title\": \"Bookstores\"}, {\"category\": [], \"alias\": \"comicbooks\", \"title\": \"Comic Books\"}, {\"category\": [], \"alias\": \"musicvideo\", \"title\": \"Music & DVDs\"}, {\"category\": [], \"alias\": \"mags\", \"title\": \"Newspapers & Magazines\"}, {\"category\": [], \"alias\": \"videoandgames\", \"title\": \"Videos and Video Game Rental\"}, {\"category\": [], \"alias\": \"vinyl_records\", \"title\": \"Vinyl Records\"}], \"alias\": \"media\", \"title\": \"Books, Mags, Music and Video\"}, {\"category\": [], \"alias\": \"bridal\", \"title\": \"Bridal\"}, {\"category\": [], \"alias\": \"computers\", \"title\": \"Computers\"}, {\"category\": [], \"alias\": \"cosmetics\", \"title\": \"Cosmetics & Beauty Supply\"}, {\"category\": [], \"alias\": \"deptstores\", \"title\": \"Department Stores\"}, {\"category\": [], \"alias\": \"discountstore\", \"title\": \"Discount Store\"}, {\"category\": [], \"alias\": \"drugstores\", \"title\": \"Drugstores\"}, {\"category\": [], \"alias\": \"electronics\", \"title\": \"Electronics\"}, {\"category\": [], \"alias\": \"opticians\", \"title\": \"Eyewear & Opticians\"}, {\"category\": [{\"category\": [], \"alias\": \"accessories\", \"title\": \"Accessories\"}, {\"category\": [], \"alias\": \"childcloth\", \"title\": \"Children's Clothing\"}, {\"category\": [], \"alias\": \"deptstores\", \"title\": \"Department Stores\"}, {\"category\": [], \"alias\": \"leather\", \"title\": \"Leather Goods\"}, {\"category\": [], \"alias\": \"lingerie\", \"title\": \"Lingerie\"}, {\"category\": [], \"alias\": \"maternity\", \"title\": \"Maternity Wear\"}, {\"category\": [], \"alias\": \"menscloth\", \"title\": \"Men's Clothing\"}, {\"category\": [], \"alias\": \"plus_size_fashion\", \"title\": \"Plus Size Fashion\"}, {\"category\": [], \"alias\": \"shoes\", \"title\": \"Shoe Stores\"}, {\"category\": [], \"alias\": \"sportswear\", \"title\": \"Sports Wear\"}, {\"category\": [], \"alias\": \"swimwear\", \"title\": \"Swimwear\"}, {\"category\": [], \"alias\": \"vintage\", \"title\": \"Used, Vintage & Consignment\"}, {\"category\": [], \"alias\": \"womenscloth\", \"title\": \"Women's Clothing\"}], \"alias\": \"fashion\", \"title\": \"Fashion\"}, {\"category\": [], \"alias\": \"fireworks\", \"title\": \"Fireworks\"}, {\"category\": [{\"category\": [], \"alias\": \"stationery\", \"title\": \"Cards & Stationery\"}, {\"category\": [], \"alias\": \"florists\", \"title\": \"Florists\"}], \"alias\": \"flowers\", \"title\": \"Flowers & Gifts\"}, {\"category\": [], \"alias\": \"guns_and_ammo\", \"title\": \"Guns & Ammo\"}, {\"category\": [], \"alias\": \"hobbyshops\", \"title\": \"Hobby Shops\"}, {\"category\": [{\"category\": [], \"alias\": \"appliances\", \"title\": \"Appliances\"}, {\"category\": [], \"alias\": \"furniture\", \"title\": \"Furniture Stores\"}, {\"category\": [], \"alias\": \"hardware\", \"title\": \"Hardware Stores\"}, {\"category\": [], \"alias\": \"homedecor\", \"title\": \"Home Decor\"}, {\"category\": [], \"alias\": \"hottubandpool\", \"title\": \"Hot Tub and Pool\"}, {\"category\": [], \"alias\": \"kitchenandbath\", \"title\": \"Kitchen & Bath\"}, {\"category\": [], \"alias\": \"mattresses\", \"title\": \"Mattresses\"}, {\"category\": [], \"alias\": \"gardening\", \"title\": \"Nurseries & Gardening\"}], \"alias\": \"homeandgarden\", \"title\": \"Home & Garden\"}, {\"category\": [], \"alias\": \"jewelry\", \"title\": \"Jewelry\"}, {\"category\": [], \"alias\": \"knittingsupplies\", \"title\": \"Knitting Supplies\"}, {\"category\": [], \"alias\": \"luggage\", \"title\": \"Luggage\"}, {\"category\": [], \"alias\": \"mobilephones\", \"title\": \"Mobile Phones\"}, {\"category\": [], \"alias\": \"musicalinstrumentsandteachers\", \"title\": \"Musical Instruments & Teachers\"}, {\"category\": [], \"alias\": \"officeequipment\", \"title\": \"Office Equipment\"}, {\"category\": [], \"alias\": \"outlet_stores\", \"title\": \"Outlet Stores\"}, {\"category\": [], \"alias\": \"pawn\", \"title\": \"Pawn Shops\"}, {\"category\": [], \"alias\": \"personal_shopping\", \"title\": \"Personal Shopping\"}, {\"category\": [], \"alias\": \"photographystores\", \"title\": \"Photography Stores & Services\"}, {\"category\": [], \"alias\": \"shoppingcenters\", \"title\": \"Shopping Centers\"}, {\"category\": [{\"category\": [], \"alias\": \"bikes\", \"title\": \"Bikes\"}, {\"category\": [], \"alias\": \"outdoorgear\", \"title\": \"Outdoor Gear\"}, {\"category\": [], \"alias\": \"sportswear\", \"title\": \"Sports Wear\"}], \"alias\": \"sportgoods\", \"title\": \"Sporting Goods\"}, {\"category\": [], \"alias\": \"thrift_stores\", \"title\": \"Thrift Stores\"}, {\"category\": [], \"alias\": \"tobaccoshops\", \"title\": \"Tobacco Shops\"}, {\"category\": [], \"alias\": \"toys\", \"title\": \"Toy Stores\"}, {\"category\": [], \"alias\": \"watches\", \"title\": \"Watches\"}, {\"category\": [], \"alias\": \"wholesale_stores\", \"title\": \"Wholesale Stores\"}], \"alias\": \"shopping\", \"title\": \"Shopping\"}]";
	HashMap<String, String> categoryMap;
	OAuthService service;
	Token accessToken;
	 
	public Yelp(String query, String location_lat, String location_long) {
		this.query = query;
		this.location_lat = location_lat;
		this.location_long = location_long;
		this.service =
		        new ServiceBuilder().provider(TwoStepAuth.class).apiKey(CONSUMER_KEY)
		            .apiSecret(CONSUMER_SECRET).build();
		    this.accessToken = new Token(TOKEN, TOKEN_SECRET);
	}
	
	public String getPlaces(){
		int messageType = 0;
		String[] messageArray = query.split("\\s+");
		for(String queryterm:messageArray){
			if(queryterm.equals("in")){
				messageType = 1;
			}
		}
		
		if(messageType == 0){ //find place based on location (lat, lng) + place name
			String searchResponseJSON = searchForBusinessesByLocation(query, location_lat, location_long);
			System.out.println(query + " " + location_lat + " " + location_long);
			JSONParser parser = new JSONParser();
			JSONObject response = null;
			try {
				response = (JSONObject) parser.parse(searchResponseJSON);
				System.out.println(searchResponseJSON);
			} catch (ParseException pe) {
				pe.printStackTrace();
				return "Error: could not find the requested place";
			}

			JSONArray businesses = (JSONArray) response.get("businesses");
			JSONObject firstBusiness = (JSONObject) businesses.get(0);
			String firstBusinessID = firstBusiness.get("id").toString();
			System.out.println(String.format(
					"%s businesses found, querying business info for the top result \"%s\" ...",
					businesses.size(), firstBusinessID));

			// Select the first business and display business details
			String businessResponseJSON = searchByBusinessId(firstBusinessID.toString());
			System.out.println(String.format("Result for business \"%s\" found:", firstBusinessID));
			System.out.println(businessResponseJSON);
			JSONObject businessJSON = null;
			try{
				businessJSON = (JSONObject) parser.parse(businessResponseJSON);
			}catch (ParseException pe) {
				pe.printStackTrace();
				return "Error: could not find any place matching" + query;
			}
			System.out.println(businessJSON);
			String name = "";
			if(businessJSON.get("name") != null){
				name = businessJSON.get("name").toString();
			}
			String phone = "";
			if(businessJSON.get("phone") != null){
				phone = businessJSON.get("phone").toString();
			}
			String address = "";
			if(((JSONArray)((JSONObject)(businessJSON.get("location"))).get("address")).get(0) != null){
				address = ((JSONArray)((JSONObject)(businessJSON.get("location"))).get("address")).get(0).toString()
						+ ", " + ((JSONObject)(businessJSON.get("location"))).get("city").toString()
						+ ", " + ((JSONObject)(businessJSON.get("location"))).get("state_code").toString()
						+ ", " + ((JSONObject)(businessJSON.get("location"))).get("postal_code").toString();
			}
			String rating = "";
			if(businessJSON.get("rating") != null){
				rating = "Rating: " + businessJSON.get("rating").toString();
			}
			String numReviews = "";
			if(businessJSON.get("review_count") != null){
				numReviews = "Number of Reviews: " + businessJSON.get("review_count");
			}
			String tip = "";
			if(businessJSON.get("snippet_text") != null){
				tip = "Tip: " + businessJSON.get("snippet_text");
			}
			String isOpen = "";
			if(businessJSON.get("is_closed") != null){
				isOpen = (boolean) businessJSON.get("is_closed") ? "Closed" : "Open";
			}
			String resultString = name + "\n" + 
									 phone + '\n' +
									 address + "\n" +
									 rating + "\n" +
									 numReviews + "\n" +
									 tip + "\n" +
									 isOpen;

		    return resultString;
			
		}
		else if(messageType == 1){ //find place based on location(string) + category
			String[] queryAsWords = query.split("\\bin\\b");
			String category = queryAsWords[0].trim();
			String location = queryAsWords[1].trim();
			
			System.out.println(location + " " + category + " " + categoryMap.get(category));
			String searchResponseJSON = searchForBusinessesByLocationAndCategory(location, categoryMap.get(category));
			System.out.println(location + " " + category);
			JSONParser parser = new JSONParser();
			JSONObject response = null;
			try {
				response = (JSONObject) parser.parse(searchResponseJSON);
				System.out.println(searchResponseJSON);
			} catch (ParseException pe) {
				pe.printStackTrace();
				return "Error: could not find the requested place";
			}
			JSONArray businesses = (JSONArray) response.get("businesses");
			String resultString = "";
			for(int i = 0; i< businesses.size(); i++){
				JSONObject firstBusiness = (JSONObject) businesses.get(i);
				String firstBusinessID = firstBusiness.get("id").toString();
				System.out.println(String.format(
						"%s businesses found, querying business info for the top result \"%s\" ...",
						businesses.size(), firstBusinessID));
	
				// Select the first business and display business details
				String businessResponseJSON = searchByBusinessId(firstBusinessID.toString());
				System.out.println(String.format("Result for business \"%s\" found:", firstBusinessID));
				System.out.println(businessResponseJSON);
				JSONObject businessJSON = null;
				try{
					businessJSON = (JSONObject) parser.parse(businessResponseJSON);
				}catch (ParseException pe) {
					pe.printStackTrace();
					return "Error: could not find any place matching" + query;
				}
				System.out.println(businessJSON);
				String name = "";
				if(businessJSON.get("name") != null){
					name = businessJSON.get("name").toString();
				}
				String phone = "";
				if(businessJSON.get("phone") != null){
					phone = businessJSON.get("phone").toString();
				}
				String address = "";
				if(((JSONArray)((JSONObject)(businessJSON.get("location"))).get("address")).get(0) != null){
					address = ((JSONArray)((JSONObject)(businessJSON.get("location"))).get("address")).get(0).toString()
							+ ", " + ((JSONObject)(businessJSON.get("location"))).get("city").toString()
							+ ", " + ((JSONObject)(businessJSON.get("location"))).get("state_code").toString()
							+ ", " + ((JSONObject)(businessJSON.get("location"))).get("postal_code").toString();
				}
				String rating = "";
				if(businessJSON.get("rating") != null){
					rating = "Rating: " + businessJSON.get("rating").toString();
				}
				String numReviews = "";
				if(businessJSON.get("review_count") != null){
					numReviews = "Number of Reviews: " + businessJSON.get("review_count");
				}
				String tip = "";
				if(businessJSON.get("snippet_text") != null){
					tip = "Tip: " + businessJSON.get("snippet_text");
				}
				String isOpen = "";
				if(businessJSON.get("is_closed") != null){
					isOpen = (boolean) businessJSON.get("is_closed") ? "Closed" : "Open";
				}
				resultString += "\n" + name + "\n" + 
										 phone + '\n' +
										 address + "\n" +
										 rating + "\n" +
										 numReviews + "\n" +
										 tip + "\n" +
										 isOpen + "\n";
			}
			System.out.println(resultString);

		    return resultString;
			
			
			//for(String name: categoryMap.keySet()){
			//	System.out.println(name + " " + categoryMap.get(name));
			//}
			
		}else {
			return "Use this syntax: Place Name (Ex. McDonald's)\nor Category in Location (Ex. Hotel in New York)";
		}		
	}
	
	public void loadCategories(){
		categoryMap = new HashMap<>();
		JSONParser parser = new JSONParser();
		JSONArray categoryJSON = null;
		try {
			categoryJSON = (JSONArray) parser.parse(categoryJSONList);
		} catch (ParseException pe) {
			//shouldnt happen
			pe.printStackTrace();
		}
		System.out.println(categoryJSON);
		for(int i = 0; i < categoryJSON.size(); i++){
			JSONArray categoryListArray = (JSONArray) ((JSONObject) categoryJSON.get(i)).get("category");
		
			System.out.println(categoryListArray);
			for(Object category:categoryListArray){
				categoryMap.put(((JSONObject)category).get("title").toString().toLowerCase(), ((JSONObject)category).get("alias").toString());
			}
		}
		
	}
	public String searchForBusinessesByLocationAndCategory(String location, String category) {
		OAuthRequest request = createOAuthRequest(YELP_SEARCH_PATH);
		request.addQuerystringParameter("location", location);
		request.addQuerystringParameter("category_filter", category);
		request.addQuerystringParameter("limit", String.valueOf(SEARCH_LIMIT));
		return sendRequestAndGetResponse(request);
	}
	
	public String searchForBusinessesByLocation(String term, String lat, String lng) {
		OAuthRequest request = createOAuthRequest(YELP_SEARCH_PATH);
		request.addQuerystringParameter("term", term);
		request.addQuerystringParameter("ll", lat + "," + lng);
		request.addQuerystringParameter("limit", String.valueOf(SEARCH_LIMIT));
		return sendRequestAndGetResponse(request);
	}

	public String searchByBusinessId(String businessID) {
		OAuthRequest request = createOAuthRequest(BUSINESS_PATH + "/" + businessID);
		return sendRequestAndGetResponse(request);
	}

	private String sendRequestAndGetResponse(OAuthRequest request) {
		System.out.println("Querying " + request.getCompleteUrl() + " ...");
		this.service.signRequest(this.accessToken, request);
		Response response = request.send();
		return response.getBody();
	}

	private OAuthRequest createOAuthRequest(String path) {
		OAuthRequest request = new OAuthRequest(Verb.GET, "http://" + YELP_HOST + path);
		return request;
	}
	
	private static int minimum(int a, int b, int c) {                            
        return Math.min(Math.min(a, b), c);                                      
    }                                                                            
 
    public static int computeLevenshteinDistance(String str1,String str2) {      
        int[][] distance = new int[str1.length() + 1][str2.length() + 1];        
 
        for (int i = 0; i <= str1.length(); i++)                                 
            distance[i][0] = i;                                                  
        for (int j = 1; j <= str2.length(); j++)                                 
            distance[0][j] = j;                                                  
 
        for (int i = 1; i <= str1.length(); i++)                                 
            for (int j = 1; j <= str2.length(); j++)                             
                distance[i][j] = minimum(                                        
                        distance[i - 1][j] + 1,                                  
                        distance[i][j - 1] + 1,                                  
                        distance[i - 1][j - 1] + ((str1.charAt(i - 1) == str2.charAt(j - 1)) ? 0 : 1));
 
        return distance[str1.length()][str2.length()];                           
    }
	
}

