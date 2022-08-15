(ns helix.vura.calendar
  (:require
    [vura.core :as vura]
    [helix.styled-components :refer [defstyled]]
    [helix.dom :as d]
    [helix.core :refer [create-context defnc defhook $]]
    [helix.hooks :as hooks]))



;; CALENDAR

(def ^:dynamic *calendar-events* (create-context))
(def ^:dynamic *calendar-selected* (create-context))
(def ^:dynamic *calendar-disabled* (create-context))
(def ^:dynamic *calendar-control* (create-context))
(def ^:dynamic *calendar-opened* (create-context))
(def ^:dynamic *calendar-state* (create-context))


(defhook use-calendar-events [] (hooks/use-context *calendar-events*))
(defhook use-calendar-state [] (hooks/use-context *calendar-state*))


(defnc CalendarDay 
  [{:keys [value
           day-in-month
           className] :as props}]
  (let [{on-select :on-day-change} (hooks/use-context *calendar-events*)
        is-disabled (hooks/use-context *calendar-disabled*)
        is-selected (hooks/use-context *calendar-selected*)
        disabled (when (ifn? is-disabled) (is-disabled props))
        selected (when (ifn? is-selected) (is-selected props))] 
    (d/div
      {:class className}
      (d/div
        {:class (cond-> ["day"]
                  selected (conj "selected")
                  disabled (conj "disabled")
                  (nil? value) (conj "empty"))
         :onClick (fn [] 
                    (when-not disabled 
                      (when (fn? on-select) 
                        (on-select day-in-month))))}
        (d/div (or day-in-month " "))))))


(defstyled calendar-day CalendarDay
  {:border-collapse "collapse"
   :border "1px solid transparent"
   ".day"
   {:text-align "center"
    :font-size "10"
    :user-select "none"
    :padding 3
    :width 20
    :border-collapse "collapse"
    :border "1px solid transparent"
    :cursor "pointer"
    ".empty" {:cursor "default"}}})


(def ^:dynamic *calendar-day* (create-context calendar-day))


(defnc CalendarWeek [{:keys [days className]}]
  (let [days (group-by :day days)
        calendar-day (hooks/use-context *calendar-day*)]
    (d/div 
      {:class className}
      (d/div 
        {:class "week-days"}
        ($ calendar-day {:key 1 :day 1 & (get-in days [1 0] {})})
        ($ calendar-day {:key 2 :day 2 & (get-in days [2 0] {})})
        ($ calendar-day {:key 3 :day 3 & (get-in days [3 0] {})})
        ($ calendar-day {:key 4 :day 4 & (get-in days [4 0] {})})
        ($ calendar-day {:key 5 :day 5 & (get-in days [5 0] {})})
        ($ calendar-day {:key 6 :day 6 & (get-in days [6 0] {})})
        ($ calendar-day {:key 7 :day 7 & (get-in days [7 0] {})})))))

(defstyled calendar-week CalendarWeek
  {".week-days"
   {:display "flex"
    :flex-direction "row"}})


(def ^:dynamic *calendar-week* (create-context calendar-week))


(defnc CalendarMonthHeader
  [{:keys [className days]}]
  (let [week-days ["Sun" "Mon" "Tue" "Wed" "Thu" "Fri" "Sat"]
        day-names (zipmap
                    [7 1 2 3 4 5 6]
                    week-days)]
    (d/div
      {:class className}
      (map
        (fn [n]
          (d/div
            {:class "day-wrapper"
             :key n}
            (d/div {:class "day"} (get day-names n))))
        days))))


(defstyled calendar-month-header CalendarMonthHeader
  {:display "flex"
   :flex-direction "row"
   :border-radius 3
   :cursor "default"
   ".day-wrapper" 
   {:border-collapse "collapse"
    :border "1px solid transparent"
    ".day"
    {:text-align "center"
     :font-weight "500"
     :font-size "12"
     :border-collapse "collapse"
     :user-select "none"
     :padding 3
     :width 20
     :border "1px solid transparent"}}})


(def ^:dynamic *calendar-month-header* (create-context calendar-month-header))


(defnc CalendarMonth
  [{:keys [className days]}]
  (let [weeks (sort-by key (group-by :week days))
        month-header (hooks/use-context *calendar-month-header*)
        calendar-week (hooks/use-context *calendar-week*)]
    (d/div 
      {:class className}
      ($ month-header {:days (range 1 8)})
      (map
        #($ calendar-week {:key (key %) :week (key %) :days (val %)})
        weeks))))


(defstyled calendar-month CalendarMonth
  {:display "flex"
   :flex-direction "column"
   :width 220})


(defnc CalendarMonthDropdown
  [{:keys [value placeholder className] 
    :or {placeholder "-"}
    :as props}]
  (let [value (or value (vura/month? (vura/date)))
        {on-month-change :on-month-change} (use-calendar-events) 
        months (range 1 13)
        month-names ["January" 
                     "February" 
                     "March" 
                     "April" 
                     "May" 
                     "June" 
                     "July" 
                     "August" 
                     "September" 
                     "October" 
                     "November" 
                     "December"] 
        search-fn (zipmap months month-names)
        props' (assoc props 
                 :onChange on-month-change
                 :search-fn search-fn
                 :options months 
                 :position-preference popup/central-preference
                 :value value)]
    #_($ DropdownElement
       {:placeholder placeholder
        :className className
        & props'})))


(defstyled calendar-month-dropdown CalendarMonthDropdown
  {:margin "5px 0"
   :cursor "pointer"
   :input {:cursor "pointer"
           :color "red"}})


(defnc CalendarYearDropdown
  [{:keys [value placeholder className] 
    :or {placeholder "-"}
    :as props}]
  (let [value (or value (vura/year? (vura/date)))
        {on-year-change :on-year-change} (use-calendar-events) 
        props' (assoc props
                      :value value
                      :onChange on-year-change
                      :position-preference popup/central-preference
                      :options
                      (let [year (vura/year? (vura/date))]
                        (range (- year 5) (+ year 5)))) ]
    ;;
    #_($ DropdownElement
       {:placeholder placeholder
        :className className
        & props'})))


(defstyled calendar-year-dropdown CalendarYearDropdown
  {:margin "5px 0"
   :cursor "pointer"
   :input {:cursor "pointer"
           :color "red"}})




;;

(defnc TimestampCalendar
  [{:keys [year month day-in-month className]}]
  (let [now (-> (vura/date) vura/time->value)
        year (or year (vura/year? now))
        month (or month (vura/month? now))
        day-in-month (or day-in-month (vura/day-in-month? now))
        days (hooks/use-memo
               [year month] 
               (vura/calendar-frame
                 (vura/date->value (vura/date year month))
                 :month))
        {:keys [on-next-month on-prev-month]} (use-calendar-events)]
    (d/div
      {:className className}
      (d/div
        {:className "header-wrapper"}
        (d/div
          {:className "header"} 
          (d/div
            {:className "years"}
            ;; FONTAWESOME
            #_($ fa
               {:icon faAngleLeft 
                :pull "left"
                :className "button"
                :onClick on-prev-month})
            ($ calendar-year-dropdown {:value year}))
          (d/div
            {:className "months"}
            ($ calendar-month-dropdown {:value month})
            ($ fa
               {:icon faAngleRight :pull "right"
                :className "button"
                :onClick on-next-month}))))
      (d/div
        {:className "content-wrapper"} 
        (d/div
          {:className "content"}
          ($ calendar-month {:value day-in-month :days days}))))))



(defstyled timestamp-calendar TimestampCalendar
  {:display "flex"
   :flex-direction "column"
   :border-radius 3
   :padding 7
   :width 230 
   :height 190
   (str popup/dropdown-container) {:overflow "hidden"}
   ".header-wrapper" {:display "flex" :justify-content "center" :flex-grow "1"}
   ".header"
   {:display "flex"
    :justify-content "space-between"
    :width 200
    :height 38
    ".years"
    {:position "relative"
     :display "flex"
     :align-items "center"}
    ".months"
    {:position "relative"
     :display "flex"
     :align-items "center"}}
   ".content-wrapper" 
   {:display "flex" 
    :height 150
    :justify-content "center" 
    :flex-grow "1"}})
